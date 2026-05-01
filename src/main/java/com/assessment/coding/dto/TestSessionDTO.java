package com.assessment.coding.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TestSessionDTO {
    private Long id;
    private Long studentId;
    private String studentName;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer score;
}