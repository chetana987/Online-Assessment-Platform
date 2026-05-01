package com.assessment.coding.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseResultResponseDTO {
    private Long id;
    private Integer testCaseIndex;
    private String input;
    private String expectedOutput;
    private String actualOutput;
    private Boolean isPassed;
    private Long executionTime;
    private String errorOutput;
}