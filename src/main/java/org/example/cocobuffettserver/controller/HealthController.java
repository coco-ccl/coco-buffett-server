package org.example.cocobuffettserver.controller;

import org.example.cocobuffettserver.dto.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ApiResponse healthCheck() {
        return ApiResponse.success();
    }
}