package com.assessment.coding.dto;

import lombok.Data;

@Data
public class TestStartRequestDTO {
    private Long testId;
    private String accessCode;
    private Long studentId;
    private String studentName;
}