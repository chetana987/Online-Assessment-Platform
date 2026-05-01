package com.assessment.coding.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubmissionDetailAdminDTO {
    private Long submissionId;
    private Long sessionId;
    private Long testId;
    private String testTitle;
    private Long questionId;
    private String questionTitle;
    private Long studentId;
    private String studentName;
    private String language;
    private String status;
    private Integer passedCount;
    private Integer totalCount;
    private Double score;
    private LocalDateTime submittedAt;
}