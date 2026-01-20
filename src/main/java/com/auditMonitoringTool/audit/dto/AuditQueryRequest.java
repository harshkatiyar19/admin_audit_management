package com.auditMonitoringTool.audit.dto;

import java.sql.Timestamp;
import java.util.List;

public record AuditQueryRequest(
                Timestamp fromDate,
                Timestamp toDate,
                List<String> actionTypes,
                List<String> clientCodes,
                List<String> usernames
) {
}
