package com.assessment.coding.controller;

import com.assessment.coding.dto.*;
import com.assessment.coding.entity.Submission;
import com.assessment.coding.entity.Test;
import com.assessment.coding.entity.TestSession;
import com.assessment.coding.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TestRepository testRepository;
    private final TestSessionRepository testSessionRepository;
    private final SubmissionRepository submissionRepository;
    private final QuestionRepository questionRepository;

    @GetMapping("/tests")
    public ResponseEntity<ApiResponse<List<TestResponseDTO>>> getAllTests() {
        List<TestResponseDTO> tests = testRepository.findAll().stream()
                .map(t -> TestResponseDTO.builder()
                        .id(t.getId())
                        .title(t.getTitle())
                        .description(t.getDescription())
                        .accessCode(t.getAccessCode())
                        .duration(t.getDuration())
                        .questionCount(t.getQuestions() != null ? t.getQuestions().size() : 0)
                        .isActive(t.getIsActive())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(tests));
    }

    @GetMapping("/test/{id}/results")
    public ResponseEntity<ApiResponse<List<TestSessionDTO>>> getTestResults(@PathVariable Long id) {
        List<TestSession> sessions = testSessionRepository.findByTestId(id);
        List<TestSessionDTO> results = sessions.stream()
                .map(s -> TestSessionDTO.builder()
                        .id(s.getId())
                        .studentId(s.getStudentId())
                        .studentName(s.getStudentName())
                        .status(s.getStatus().name())
                        .startTime(s.getStartTime())
                        .endTime(s.getEndTime())
                        .score(s.getScore())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/submissions")
    public ResponseEntity<ApiResponse<List<SubmissionDetailAdminDTO>>> getAllSubmissions() {
        List<Submission> submissions = submissionRepository.findAll();
        List<SubmissionDetailAdminDTO> details = submissions.stream()
                .map(s -> {
                    String testTitle = s.getTestSession() != null && s.getTestSession().getTest() != null
                            ? s.getTestSession().getTest().getTitle() : "N/A";
                    return SubmissionDetailAdminDTO.builder()
                            .submissionId(s.getId())
                            .sessionId(s.getTestSession() != null ? s.getTestSession().getId() : null)
                            .testId(s.getTestSession() != null && s.getTestSession().getTest() != null
                                    ? s.getTestSession().getTest().getId() : null)
                            .testTitle(testTitle)
                            .questionId(s.getQuestion() != null ? s.getQuestion().getId() : null)
                            .questionTitle(s.getQuestion() != null ? s.getQuestion().getTitle() : "N/A")
                            .studentId(s.getUserId())
                            .language(s.getLanguage() != null ? s.getLanguage().name() : "N/A")
                            .status(s.getStatus() != null ? s.getStatus().name() : "N/A")
                            .passedCount(s.getPassedCount())
                            .totalCount(s.getTotalCount())
                            .score(s.getScore())
                            .build();
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(details));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardDTO>> getDashboard() {
        long totalTests = testRepository.count();
        long totalSessions = testSessionRepository.count();
        long totalSubmissions = submissionRepository.count();
        long totalQuestions = questionRepository.count();
        
        // Get scores from test sessions - each student has unique session
        List<TestSession> sessions = testSessionRepository.findAll();
        
        List<DashboardDTO.ScoreData> scoreData = new ArrayList<>();
        
        for (TestSession session : sessions) {
            if (session.getStudentName() == null) continue;
            
            // Get submissions for this session
            List<Submission> sessionSubs = submissionRepository.findByTestSessionId(session.getId());
            if (sessionSubs.isEmpty()) continue;
            
            double avgScore = sessionSubs.stream()
                    .mapToInt(s -> s.getScore() != null ? s.getScore().intValue() : 0)
                    .average()
                    .orElse(0.0);
            
            scoreData.add(DashboardDTO.ScoreData.builder()
                    .studentName(session.getStudentName())
                    .score((int) avgScore)
                    .build());
        }
        
        DashboardDTO dashboard = DashboardDTO.builder()
                .totalTests(totalTests)
                .totalSessions(totalSessions)
                .totalSubmissions(totalSubmissions)
                .totalQuestions(totalQuestions)
                .scoreData(scoreData)
                .build();

        return ResponseEntity.ok(ApiResponse.success(dashboard));
    }
}