package com.assessment.coding.service;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CodeExecutionService {

    private static final String JUDGE0_URL = "https://judge0-ce.p.rapidapi.com";
    private static final String FREE_JUDGE0_URL = "https://ce.judge0.com";
    
    private final RestTemplate restTemplate;

    public ExecutionResult executeCode(String sourceCode, String language, String input) {
        try {
            Map<String, Object> requestBody = buildRequest(sourceCode, language, input);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            // Submit code and wait for result
            ResponseEntity<Map> response = restTemplate.postForEntity(
                FREE_JUDGE0_URL + "/submissions?wait=true", 
                request, 
                Map.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return parseResponse(response.getBody());
            }
            
            return ExecutionResult.builder()
                    .success(false)
                    .output("")
                    .error("Execution failed: Invalid response")
                    .build();
                    
        } catch (Exception e) {
            return ExecutionResult.builder()
                    .success(false)
                    .output("")
                    .error("Execution error: " + e.getMessage())
                    .build();
        }
    }

    private Map<String, Object> buildRequest(String sourceCode, String language, String input) {
        Map<String, Object> request = new HashMap<>();
        
        request.put("language_id", getLanguageId(language));
        request.put("source_code", sourceCode);
        
        if (input != null && !input.isEmpty()) {
            request.put("stdin", input);
        }
        
        request.put("cpu_time_limit", 2);
        request.put("memory_limit", 128000);
        
        return request;
    }

    private ExecutionResult parseResponse(Map response) {
        String stdout = getStringValue(response, "stdout");
        String stderr = getStringValue(response, "stderr");
        String compileOutput = getStringValue(response, "compile_output");
        String status = getStringValue(response, "status");
        Integer time = getIntValue(response, "time");
        
        String error = null;
        if (stderr != null && !stderr.isEmpty()) {
            error = stderr;
        } else if (compileOutput != null && !compileOutput.isEmpty()) {
            error = compileOutput;
        } else if (status != null && !status.equals("Accepted")) {
            error = "Status: " + status;
        }
        
        boolean success = "Accepted".equals(status);
        
        return ExecutionResult.builder()
                .success(success)
                .output(stdout != null ? stdout.trim() : "")
                .error(error)
                .executionTime(time != null ? (long) time * 1000 : 0L)
                .build();
    }
    
    private String getStringValue(Map map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }
    
    private Integer getIntValue(Map map, String key) {
        Object value = map.get(key);
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Number) return ((Number) value).intValue();
        return null;
    }

    private Integer getLanguageId(String language) {
        String lang = language.toUpperCase();
        if (lang.equals("PYTHON")) return 71;
        if (lang.equals("JAVA")) return 62;
        if (lang.equals("CPP") || lang.equals("C++")) return 54;
        if (lang.equals("C")) return 50;
        if (lang.equals("JAVASCRIPT")) return 63;
        return 71;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExecutionResult {
        private boolean success;
        private String output;
        private String error;
        private Long executionTime;
    }
}