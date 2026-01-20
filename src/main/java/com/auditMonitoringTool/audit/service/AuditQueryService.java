package com.auditMonitoringTool.audit.service;

import com.auditMonitoringTool.audit.dto.*;
import com.auditMonitoringTool.audit.enums.AdminAction;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Service;
import com.auditMonitoringTool.audit.repository.AuditLogRepository;

@Service
@Slf4j
public class AuditQueryService {

    private final AuditLogRepository repository;
    private final ObjectMapper objectMapper;

    public AuditQueryService(AuditLogRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }



    public List<String> getFilterUsernames() {
        return repository.findUniqueUsernames();
    }

    public List<String> getFilterClientCodes() {
        return repository.findUniqueClientCodes();
    }

    public AuditInfoDto queryAuditsDefault() {

        Timestamp current = repository.findLatestTimestampNative();
        Timestamp prevWeek = Timestamp.valueOf(current.toLocalDateTime().minusWeeks(1).toLocalDate().atStartOfDay());

        return query(new AuditQueryRequest(
                prevWeek,
                current,
                List.of(),
                List.of(),
                List.of()
        ));
    }


    private <T> List<T> normalizeList(List<T> list) {
        return (list == null || list.isEmpty()) ? null : list;
    }

    public AuditInfoDto query(AuditQueryRequest request) {

        checkTimeStampData(request.fromDate(),request.toDate());

        log.info("AUDIT QUERY: from={}, to={}, actions={}, clients={}, users={}",
                request.fromDate(),
                request.toDate(),
                request.actionTypes(),
                request.clientCodes(),
                request.usernames());

        List<String> actionTypes = normalizeList(request.actionTypes());

        List<String> clientCodes = normalizeList(request.clientCodes());
        List<String> usernames = normalizeList(request.usernames());

        String jsonResult = repository.fetchAuditInfoJson(
                request.fromDate(), request.toDate(), actionTypes, clientCodes, usernames
        );

        try {
            return objectMapper.readValue(jsonResult, AuditInfoDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse audit info JSON", e);
        }
    }


    public List<ActionCountDto> actionTypeCountUsername(UserCountRequestDto request) {
        checkTimeStampData(request.fromDate(),request.toDate());

        return repository.getUserNameActionCount(request.fdsUsername(),request.fromDate(),request.toDate());
    }

    public List<ActionCountDto> actionTypeCountClient(ClientCountRequestDto request) {

        checkTimeStampData(request.fromDate(),request.toDate());

        return repository.getClientActionCount(request.clientCode(),request.fromDate(),request.toDate());
    }

    private void checkTimeStampData(Timestamp fromDate, Timestamp toDate) {
        if (fromDate == null || toDate == null)
            throw new IllegalArgumentException("fromDate and toDate are required");

        if (fromDate.after(toDate))
            throw new IllegalArgumentException("fromDate must be before toDate");

    }

    public Timestamp getLatestTimestamp() {
        return repository.findLatestTimestampNative();
    }
}
