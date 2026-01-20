package com.auditMonitoringTool.audit.enums;

import lombok.Getter;

@Getter
public enum AdminAction {
    ALERT_JOB("CentralRMS Alert Job"),
    SYNC_PRICING_CONFIG("Pricing Config Sync"),
    SYNC_SPECIAL_EVENT_CATEGORIES("Special Event Categories Sync"),
    INSTRUCTION_SHEET_UPLOAD("Instruction Sheet Upload"),
    ADD_NEW_CLIENT("Add a new client database"),
    SYNC_ROOMS("Rooms Sync"),
    SYNC_FORECAST_GROUP("Forecast Group Sync"),
    SYNC_ROLES_PERMISSIONS("Roles and Permissions Sync"),
    SYNC_USERS("Users Sync"),
    DYNAMIC("Used for Runtime Action"),
    GRANT_CORE_ADMIN_ACCESS("Grant Core Admin Access"),
    REVOKE_CORE_ADMIN_ACCESS("Revoke Core Admin");

    private final String label;

    AdminAction(String label) {
        this.label = label;
    }

}
