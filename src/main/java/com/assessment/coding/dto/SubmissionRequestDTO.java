package com.assessment.coding.dto;

import com.assessment.coding.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionRequestDTO {

    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotNull(message = "Language is required")
    private Language language;

    @NotBlank(message = "Source code is required")
    private String sourceCode;
}