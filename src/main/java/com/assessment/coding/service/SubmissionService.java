package com.assessment.coding.service;

import com.assessment.coding.dto.*;
import com.assessment.coding.entity.*;
import com.assessment.coding.enums.SubmissionStatus;
import com.assessment.coding.repository.*;
import com.assessment.coding.repository.TestCaseRepository;
import com.assessment.coding.repository.TestSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmissionResultRepository submissionResultRepository;
    private final QuestionRepository questionRepository;
    private final TestCaseRepository testCaseRepository;
    private final CodeExecutionService executionService;
    private final TestSessionRepository testSessionRepository;

    @Transactional
    public SubmissionDetailResponseDTO createSubmission(SubmissionRequestDTO request, Long userId, Long testSessionId) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + request.getQuestionId()));
        
        TestSession testSession = null;
        if (testSessionId != null) {
            testSession = testSessionRepository.findById(testSessionId).orElse(null);
        }

        Submission submission = Submission.builder()
                .userId(userId)
                .question(question)
                .testSession(testSession)
                .language(request.getLanguage())
                .sourceCode(request.getSourceCode())
                .status(SubmissionStatus.PROCESSING)
                .build();

        submission = submissionRepository.save(submission);
        log.info("Created submission for user {} question {}", userId, request.getQuestionId());

        Submission evaluated = evaluateSubmission(submission);
        
        return mapToDetailResponseDTO(evaluated);
    }

    @Transactional
    public Submission evaluateSubmission(Submission submission) {
        Question question = submission.getQuestion();
        List<TestCase> testCases = testCaseRepository.findByQuestionIdAndIsHiddenFalse(question.getId());

        if (testCases.isEmpty()) {
            testCases = testCaseRepository.findByQuestionId(question.getId());
        }

        int passedCount = 0;
        int totalCount = testCases.size();
        long totalExecutionTime = 0;
        StringBuilder errorMessage = new StringBuilder();
        
        List<SubmissionResult> results = new ArrayList<>();

        for (int i = 0; i < testCases.size(); i++) {
            TestCase testCase = testCases.get(i);
            String testInput = testCase.getInput() != null ? testCase.getInput() : "";
            
            // Execute code with test input via stdin
            // No code modification needed - Judge0 handles stdin
            CodeExecutionService.ExecutionResult execResult = executionService.executeCode(
                    submission.getSourceCode(),
                    submission.getLanguage().getValue(),
                    testInput
            );

            String actualOutput = execResult.getOutput();
            String expectedOutput = testCase.getExpectedOutput();
            
            boolean isPassed = compareOutput(actualOutput, expectedOutput);
            
            SubmissionResult result = SubmissionResult.builder()
                    .submission(submission)
                    .testCase(testCase)
                    .testCaseIndex(i + 1)
                    .actualOutput(actualOutput)
                    .expectedOutput(expectedOutput)
                    .isPassed(isPassed)
                    .executionTime(execResult.getExecutionTime())
                    .errorOutput(execResult.getError())
                    .build();
            
            results.add(submissionResultRepository.save(result));

            if (isPassed) {
                passedCount++;
            } else if (execResult.getError() != null && !execResult.getError().isEmpty()) {
                errorMessage.append("Test case ").append(i + 1).append(": ").append(execResult.getError()).append("; ");
            }

            totalExecutionTime += execResult.getExecutionTime() != null ? execResult.getExecutionTime() : 0L;
        }

        for (SubmissionResult result : results) {
            submission.addResult(result);
        }

        submission.setPassedCount(passedCount);
        submission.setTotalCount(totalCount);
        submission.setExecutionTime(totalExecutionTime);

        double score = totalCount > 0 ? (double) passedCount / totalCount * 100.0 : 0.0;
        submission.setScore(score);

        if (passedCount == totalCount) {
            submission.setStatus(SubmissionStatus.PASSED);
        } else if (passedCount == 0) {
            submission.setStatus(SubmissionStatus.FAILED);
        } else {
            submission.setStatus(SubmissionStatus.PARTIAL);
        }

        if (errorMessage.length() > 0) {
            submission.setErrorMessage(errorMessage.toString());
        }

        return submissionRepository.save(submission);
    }

    @Transactional(readOnly = true)
    public SubmissionDetailResponseDTO getSubmissionById(Long id) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + id));
        return mapToDetailResponseDTO(submission);
    }

    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getSubmissionsByUserId(Long userId) {
        return submissionRepository.findByUserId(userId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SubmissionResponseDTO> getSubmissionsByQuestionId(Long questionId) {
        return submissionRepository.findByQuestionId(questionId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private boolean compareOutput(String actual, String expected) {
        if (actual == null && expected == null) {
            return true;
        }
        if (actual == null || expected == null) {
            return false;
        }
        
        // Normalize: trim, remove trailing whitespace, normalize line endings
        String actualNormalized = actual.trim().replace("\r\n", "\n").replace("\r", "\n").replaceAll("\\s+$", "");
        String expectedNormalized = expected.trim().replace("\r\n", "\n").replace("\r", "\n").replaceAll("\\s+$", "");
        
        return actualNormalized.equals(expectedNormalized);
    }
    
    private String prependInputCode(String language, String input, String userCode) {
        // No modification needed - test input is sent via stdin to Judge0
        return userCode;
    }

    private SubmissionResponseDTO mapToResponseDTO(Submission submission) {
        return SubmissionResponseDTO.builder()
                .id(submission.getId())
                .userId(submission.getUserId())
                .questionId(submission.getQuestion().getId())
                .language(submission.getLanguage())
                .status(submission.getStatus())
                .passedCount(submission.getPassedCount())
                .totalCount(submission.getTotalCount())
                .score(submission.getScore())
                .executionTime(submission.getExecutionTime())
                .errorMessage(submission.getErrorMessage())
                .createdAt(submission.getCreatedAt())
                .build();
    }

    private SubmissionDetailResponseDTO mapToDetailResponseDTO(Submission submission) {
        List<TestCaseResultResponseDTO> results = submission.getResults().stream()
                .map(r -> TestCaseResultResponseDTO.builder()
                        .id(r.getId())
                        .testCaseIndex(r.getTestCaseIndex())
                        .input(r.getTestCase() != null ? r.getTestCase().getInput() : null)
                        .expectedOutput(r.getExpectedOutput())
                        .actualOutput(r.getActualOutput())
                        .isPassed(r.getIsPassed())
                        .executionTime(r.getExecutionTime())
                        .errorOutput(r.getErrorOutput())
                        .build())
                .collect(Collectors.toList());

        return SubmissionDetailResponseDTO.builder()
                .id(submission.getId())
                .userId(submission.getUserId())
                .questionId(submission.getQuestion().getId())
                .questionTitle(submission.getQuestion().getTitle())
                .language(submission.getLanguage())
                .sourceCode(submission.getSourceCode())
                .status(submission.getStatus())
                .passedCount(submission.getPassedCount())
                .totalCount(submission.getTotalCount())
                .score(submission.getScore())
                .executionTime(submission.getExecutionTime())
                .errorMessage(submission.getErrorMessage())
                .createdAt(submission.getCreatedAt())
                .results(results)
                .build();
    }
}