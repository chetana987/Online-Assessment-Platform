package com.assessment.coding.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardDTO {
    private Long totalTests;
    private Long totalSessions;
    private Long totalSubmissions;
    private Long totalQuestions;
    private List<ScoreData> scoreData;
    
    @Data
    @Builder
    public static class ScoreData {
        private String studentName;
        private Integer score;
    }
}