package com.assessment.coding.dto;

import com.assessment.coding.enums.TestType;
import lombok.Data;
import java.util.List;

@Data
public class TestRequestDTO {
    private String title;
    private String description;
    private Integer duration;
    private TestType testType;
    private List<Long> questionIds;
    private Boolean isActive;
}