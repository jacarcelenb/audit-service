package com.ip.service.audit.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ip.service.audit.dto.AuditInfoDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @GetMapping
    public AuditInfoDTO audit(HttpServletRequest request) {

        String clientIp = extractClientIp(request);

        Map<String, String> forwardedHeaders = new HashMap<>();
        forwardedHeaders.put("X-Forwarded-For", request.getHeader("X-Forwarded-For"));
        forwardedHeaders.put("X-Forwarded-Proto", request.getHeader("X-Forwarded-Proto"));
        forwardedHeaders.put("X-Forwarded-Host", request.getHeader("X-Forwarded-Host"));

        return new AuditInfoDTO(
                clientIp,
                request.getHeader("Host"),
                request.getMethod(),
                request.getRequestURI(),
                request.getHeader("User-Agent"),
                forwardedHeaders,
                LocalDateTime.now()
        );
    }

    private String extractClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}