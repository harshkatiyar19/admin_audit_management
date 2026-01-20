package com.auditMonitoringTool.audit.dto;

import java.sql.Timestamp;

public record AuditRecordDto(
        Long id,
        String fdsUsername,
        String actionType,
        String clientCode,
        Timestamp actionTimeStamp
) {}

