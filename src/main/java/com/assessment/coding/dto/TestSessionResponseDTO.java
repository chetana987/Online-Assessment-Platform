package com.assessment.coding.dto;

import com.assessment.coding.enums.TestStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TestSessionResponseDTO {
    private Long id;
    private Long testId;
    private String testTitle;
    private Integer duration;
    private Long studentId;
    private String studentName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private TestStatus status;
    private Integer score;
    private List<QuestionResponseDTO> questions;
}