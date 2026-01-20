package com.auditMonitoringTool.audit.dto;

import java.util.List;

public record AuditInfoDto(
        List<AuditRecordDto> records,
        AuditInfoAnalyticsDto analytics
){}
