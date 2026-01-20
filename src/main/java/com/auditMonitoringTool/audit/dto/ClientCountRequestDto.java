package com.auditMonitoringTool.audit.dto;

import java.sql.Timestamp;

public record ClientCountRequestDto(
        String clientCode,
        Timestamp fromDate,
        Timestamp toDate
) {}
