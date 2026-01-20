package com.auditMonitoringTool.audit.dto;

import java.sql.Timestamp;

public record UserCountRequestDto(
        String fdsUsername,
        Timestamp fromDate,
        Timestamp toDate
) {
}
