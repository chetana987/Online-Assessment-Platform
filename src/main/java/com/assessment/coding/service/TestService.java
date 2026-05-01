package com.assessment.coding.service;

import com.assessment.coding.dto.*;
import com.assessment.coding.entity.Question;
import com.assessment.coding.entity.Test;
import com.assessment.coding.entity.TestSession;
import com.assessment.coding.enums.TestStatus;
import com.assessment.coding.repository.QuestionRepository;
import com.assessment.coding.repository.TestRepository;
import com.assessment.coding.repository.TestSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final TestSessionRepository testSessionRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public TestResponseDTO createTest(TestRequestDTO request) {
        String code;
        do {
            code = generateUniqueCode();
        } while (testRepository.findByAccessCode(code).isPresent());
        
        Test test = Test.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .accessCode(code)
                .duration(request.getDuration() != null ? request.getDuration() : 60)
                .testType(request.getTestType())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();

        if (request.getQuestionIds() != null && !request.getQuestionIds().isEmpty()) {
            List<Question> questions = questionRepository.findAllById(request.getQuestionIds());
            questions.forEach(test::addQuestion);
        }

        test = testRepository.save(test);
        return mapToDTO(test);
    }

    public List<TestResponseDTO> getAllTests() {
        return testRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TestResponseDTO> getActiveTests() {
        return testRepository.findByIsActiveTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TestResponseDTO getTestById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));
        return mapToDTO(test);
    }

    @Transactional
    public TestSessionResponseDTO startTest(TestStartRequestDTO request) {
        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new RuntimeException("Test not found"));

        if (!test.getAccessCode().equals(request.getAccessCode())) {
            throw new RuntimeException("Invalid access code");
        }

        if (!test.getIsActive()) {
            throw new RuntimeException("Test is not active");
        }

        TestSession session = TestSession.builder()
                .test(test)
                .studentId(request.getStudentId())
                .studentName(request.getStudentName())
                .startTime(LocalDateTime.now())
                .status(TestStatus.STARTED)
                .build();

        session = testSessionRepository.save(session);
        return mapSessionToDTO(session);
    }

    public TestSessionResponseDTO getSessionById(Long sessionId) {
        TestSession session = testSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        
        if (session.isExpired() && session.getStatus() == TestStatus.STARTED) {
            session.expire();
            testSessionRepository.save(session);
        }
        
        return mapSessionToDTO(session);
    }

    @Transactional
    public TestSessionResponseDTO submitTest(Long sessionId) {
        TestSession session = testSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.getStatus() != TestStatus.STARTED) {
            throw new RuntimeException("Test already submitted");
        }

        if (session.isExpired()) {
            session.expire();
            testSessionRepository.save(session);
            throw new RuntimeException("Test time expired");
        }

        session.submit();
        session = testSessionRepository.save(session);
        return mapSessionToDTO(session);
    }

    private TestResponseDTO mapToDTO(Test test) {
        return TestResponseDTO.builder()
                .id(test.getId())
                .title(test.getTitle())
                .description(test.getDescription())
                .accessCode(test.getAccessCode())
                .duration(test.getDuration())
                .testType(test.getTestType())
                .isActive(test.getIsActive())
                .questionCount(test.getQuestions() != null ? test.getQuestions().size() : 0)
                .createdAt(test.getCreatedAt() != null ? test.getCreatedAt().toString() : null)
                .build();
    }
    
    private String generateUniqueCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private TestSessionResponseDTO mapSessionToDTO(TestSession session) {
        TestSessionResponseDTO.TestSessionResponseDTOBuilder builder = TestSessionResponseDTO.builder()
                .id(session.getId())
                .testId(session.getTest().getId())
                .testTitle(session.getTest().getTitle())
                .duration(session.getTest().getDuration())
                .studentId(session.getStudentId())
                .studentName(session.getStudentName())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .status(session.getStatus())
                .score(session.getScore());

        if (session.getTest() != null && session.getTest().getQuestions() != null) {
            builder.questions(session.getTest().getQuestions().stream()
                    .map(q -> QuestionResponseDTO.builder()
                            .id(q.getId())
                            .title(q.getTitle())
                            .description(q.getDescription())
                            .difficulty(q.getDifficulty())
                            .constraints(q.getConstraints())
                            .sampleInput(q.getSampleInput())
                            .sampleOutput(q.getSampleOutput())
                            .timeLimit(q.getTimeLimit())
                            .build())
                    .collect(Collectors.toList()));
        }

        return builder.build();
    }
}