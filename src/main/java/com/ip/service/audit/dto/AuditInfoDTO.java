package com.ip.service.audit.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record AuditInfoDTO(
        String clientIp,
        String host,
        String method,
        String path,
        String userAgent,
        Map<String, String> forwardedHeaders,
        LocalDateTime timestamp
) {
}