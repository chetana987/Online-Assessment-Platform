package com.assessment.coding.dto;

import com.assessment.coding.enums.Language;
import com.assessment.coding.enums.SubmissionStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionDetailResponseDTO {
    private Long id;
    private Long userId;
    private Long questionId;
    private String questionTitle;
    private Language language;
    private String sourceCode;
    private SubmissionStatus status;
    private Integer passedCount;
    private Integer totalCount;
    private Double score;
    private Long executionTime;
    private String errorMessage;
    private Long createdAt;
    private List<TestCaseResultResponseDTO> results;
}