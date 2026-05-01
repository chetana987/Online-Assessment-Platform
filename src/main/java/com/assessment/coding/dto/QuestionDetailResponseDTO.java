package com.assessment.coding.dto;

import com.assessment.coding.enums.Difficulty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDetailResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Difficulty difficulty;
    private String constraints;
    private String sampleInput;
    private String sampleOutput;
    private Integer timeLimit;
    private List<TestCaseResponseDTO> testCases;
    private Long createdAt;
}