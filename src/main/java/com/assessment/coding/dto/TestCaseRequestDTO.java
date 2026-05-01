package com.assessment.coding.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseRequestDTO {
    private String input;
    private String expectedOutput;
    private Boolean isHidden;
    private Boolean isSample;
}