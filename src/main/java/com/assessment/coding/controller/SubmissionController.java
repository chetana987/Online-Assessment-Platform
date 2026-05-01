package com.assessment.coding.controller;

import com.assessment.coding.dto.*;
import com.assessment.coding.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubmissionDetailResponseDTO>> createSubmission(
            @Valid @RequestBody SubmissionRequestDTO request,
            @RequestParam(defaultValue = "1") Long userId,
            @RequestParam(required = false) Long testSessionId) {
        SubmissionDetailResponseDTO response = submissionService.createSubmission(request, userId, testSessionId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Submission created and evaluated", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubmissionDetailResponseDTO>> getSubmissionById(@PathVariable Long id) {
        SubmissionDetailResponseDTO response = submissionService.getSubmissionById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<SubmissionResponseDTO>>> getSubmissionsByUserId(
            @PathVariable Long userId) {
        List<SubmissionResponseDTO> submissions = submissionService.getSubmissionsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(submissions));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<List<SubmissionResponseDTO>>> getSubmissionsByQuestionId(
            @PathVariable Long questionId) {
        List<SubmissionResponseDTO> submissions = submissionService.getSubmissionsByQuestionId(questionId);
        return ResponseEntity.ok(ApiResponse.success(submissions));
    }
}