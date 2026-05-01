package com.assessment.coding.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseResponseDTO {
    private Long id;
    private String input;
    private String expectedOutput;
    private Boolean isHidden;
    private Boolean isSample;
}