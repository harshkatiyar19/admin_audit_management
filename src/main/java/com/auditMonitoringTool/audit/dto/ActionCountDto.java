package com.auditMonitoringTool.audit.dto;

public record ActionCountDto(
        String actionType,
        Long count
) {
}
