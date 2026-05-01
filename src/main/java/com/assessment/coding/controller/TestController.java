package com.assessment.coding.controller;

import com.assessment.coding.dto.*;
import com.assessment.coding.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping
    public ResponseEntity<ApiResponse<TestResponseDTO>> createTest(@RequestBody TestRequestDTO request) {
        try {
            TestResponseDTO test = testService.createTest(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Test created successfully", test));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TestResponseDTO>>> getAllTests() {
        List<TestResponseDTO> tests = testService.getAllTests();
        return ResponseEntity.ok(ApiResponse.success(tests));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<TestResponseDTO>>> getActiveTests() {
        List<TestResponseDTO> tests = testService.getActiveTests();
        return ResponseEntity.ok(ApiResponse.success(tests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TestResponseDTO>> getTestById(@PathVariable Long id) {
        try {
            TestResponseDTO test = testService.getTestById(id);
            return ResponseEntity.ok(ApiResponse.success(test));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<TestSessionResponseDTO>> startTest(@RequestBody TestStartRequestDTO request) {
        try {
            TestSessionResponseDTO session = testService.startTest(request);
            return ResponseEntity.ok(ApiResponse.success("Test started", session));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<ApiResponse<TestSessionResponseDTO>> getSession(@PathVariable Long sessionId) {
        try {
            TestSessionResponseDTO session = testService.getSessionById(sessionId);
            return ResponseEntity.ok(ApiResponse.success(session));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/session/{sessionId}/submit")
    public ResponseEntity<ApiResponse<TestSessionResponseDTO>> submitTest(@PathVariable Long sessionId) {
        try {
            TestSessionResponseDTO session = testService.submitTest(sessionId);
            return ResponseEntity.ok(ApiResponse.success("Test submitted", session));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}