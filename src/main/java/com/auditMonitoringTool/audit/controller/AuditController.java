package com.auditMonitoringTool.audit.controller;

import com.auditMonitoringTool.audit.dto.*;
import com.auditMonitoringTool.audit.service.AuditQueryService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/audits")
@RequiredArgsConstructor
@Tag(name = "Audit API", description = "APIs for querying audit logs and actions")
public class AuditController {
    private final AuditQueryService auditQueryService;

    @GetMapping("/status")
    public String status() {
        return "Audit API is running";
    }


    @PostMapping("/action-type-count/username")
    public List<ActionCountDto> actionTypeCountUsername(@RequestBody UserCountRequestDto request) {return auditQueryService.actionTypeCountUsername(request);}

    @PostMapping("/action-type-count/client")
    public List<ActionCountDto> actionTypeCountClient(@RequestBody ClientCountRequestDto request) {return auditQueryService.actionTypeCountClient(request);}

    @PostMapping("/default")
    public AuditInfoDto queryAuditsDefault2() {return auditQueryService.queryAuditsDefault();}

    @PostMapping("/query")
    public AuditInfoDto queryAudits2(@RequestBody AuditQueryRequest request) {return auditQueryService.query(request);}

    @PostMapping("/latest-timestamp")
    public Timestamp getLatestTimestamp() {return auditQueryService.getLatestTimestamp();}

    @GetMapping("/usernames")
    public List<String> getUsernames() {
        return auditQueryService.getFilterUsernames();
    }

    @GetMapping("/clients")
    public List<String> getClientCodes() {
        return auditQueryService.getFilterClientCodes();
    }
}
