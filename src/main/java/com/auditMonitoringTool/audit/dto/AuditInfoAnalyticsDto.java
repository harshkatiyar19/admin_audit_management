package com.auditMonitoringTool.audit.dto;

public record AuditInfoAnalyticsDto(
        Long count,
        String mostUsedAction,
        String mostActiveClient,
        String mostActiveUsername
){}
