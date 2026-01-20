package com.auditMonitoringTool.audit.domain;

import com.auditMonitoringTool.audit.enums.AdminAction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Timestamp;

@Entity
@Table(name = "internal_audit_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalAuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fds_user_id", nullable = false)
    private String fdsUserId;

    @Column(name = "fds_username", nullable = false)
    private String fdsUsername;

    @Column(name = "action_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdminAction actionType;

    @Column(name = "client_code", nullable = false)
    private String clientCode;

    @Column(name = "action_timestamp", nullable = false)
    private Timestamp actionTimestamp;
}
