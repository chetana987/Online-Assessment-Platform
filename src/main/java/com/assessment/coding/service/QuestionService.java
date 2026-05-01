package com.assessment.coding.service;

import com.assessment.coding.dto.*;
import com.assessment.coding.entity.Question;
import com.assessment.coding.entity.TestCase;
import com.assessment.coding.enums.Difficulty;
import com.assessment.coding.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public QuestionResponseDTO createQuestion(QuestionRequestDTO request) {
        Question question = Question.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .difficulty(request.getDifficulty())
                .constraints(request.getConstraints())
                .sampleInput(request.getSampleInput())
                .sampleOutput(request.getSampleOutput())
                .timeLimit(request.getTimeLimit())
                .build();

        if (request.getTestCases() != null) {
            for (TestCaseRequestDTO testCaseDTO : request.getTestCases()) {
                TestCase testCase = TestCase.builder()
                        .input(testCaseDTO.getInput())
                        .expectedOutput(testCaseDTO.getExpectedOutput())
                        .isHidden(testCaseDTO.getIsHidden() != null ? testCaseDTO.getIsHidden() : false)
                        .isSample(testCaseDTO.getIsSample() != null ? testCaseDTO.getIsSample() : false)
                        .build();
                question.addTestCase(testCase);
            }
        }

        Question saved = questionRepository.save(question);
        return mapToResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public QuestionDetailResponseDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
        return mapToDetailResponseDTO(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> getQuestionsByDifficulty(Difficulty difficulty) {
        return questionRepository.findByDifficulty(difficulty).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private QuestionResponseDTO mapToResponseDTO(Question question) {
        return QuestionResponseDTO.builder()
                .id(question.getId())
                .title(question.getTitle())
                .description(question.getDescription())
                .difficulty(question.getDifficulty())
                .constraints(question.getConstraints())
                .sampleInput(question.getSampleInput())
                .sampleOutput(question.getSampleOutput())
                .timeLimit(question.getTimeLimit())
                .testCaseCount(question.getTestCases() != null ? question.getTestCases().size() : 0)
                .createdAt(question.getCreatedAt())
                .build();
    }

    private QuestionDetailResponseDTO mapToDetailResponseDTO(Question question) {
        List<TestCaseResponseDTO> testCases = question.getTestCases().stream()
                .map(tc -> TestCaseResponseDTO.builder()
                        .id(tc.getId())
                        .input(tc.getInput())
                        .expectedOutput(tc.getExpectedOutput())
                        .isHidden(tc.getIsHidden())
                        .isSample(tc.getIsSample())
                        .build())
                .collect(Collectors.toList());

        return QuestionDetailResponseDTO.builder()
                .id(question.getId())
                .title(question.getTitle())
                .description(question.getDescription())
                .difficulty(question.getDifficulty())
                .constraints(question.getConstraints())
                .sampleInput(question.getSampleInput())
                .sampleOutput(question.getSampleOutput())
                .timeLimit(question.getTimeLimit())
                .testCases(testCases)
                .createdAt(question.getCreatedAt())
                .build();
    }
}