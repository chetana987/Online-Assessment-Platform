package com.assessment.coding.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public byte[] home() throws IOException {
        Resource resource = new ClassPathResource("static/index.html");
        try (InputStream is = resource.getInputStream()) {
            return StreamUtils.copyToByteArray(is);
        }
    }
    
    @GetMapping("/index.html")
    @ResponseBody
    public byte[] indexHtml() throws IOException {
        return home();
    }
}