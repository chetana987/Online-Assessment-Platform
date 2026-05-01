package com.assessment.coding.dto;

import com.assessment.coding.enums.TestStatus;
import com.assessment.coding.enums.TestType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String accessCode;
    private Integer duration;
    private TestType testType;
    private Boolean isActive;
    private Integer questionCount;
    private String createdAt;
}