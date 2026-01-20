package com.auditMonitoringTool.audit.repository;

import com.auditMonitoringTool.audit.domain.InternalAuditLogEntity;
import com.auditMonitoringTool.audit.dto.ActionCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<InternalAuditLogEntity, Long> {

        @Query(value = "SELECT DISTINCT fds_username FROM internal_audit_log ORDER BY fds_username", nativeQuery = true)
        List<String> findUniqueUsernames();

        @Query(
                value = "SELECT DISTINCT client_code FROM internal_audit_log WHERE client_code IS NOT NULL ORDER BY client_code",
                nativeQuery = true)
        List<String> findUniqueClientCodes();

        @Query(
                value = "SELECT MAX(action_timestamp) FROM internal_audit_log WHERE action_timestamp IS NOT NULL",
                nativeQuery = true)
        Timestamp findLatestTimestampNative();

        @Query(value = """
               WITH filtered_data AS (
                                      SELECT
                                            a.id AS id,
                                            a.fds_username AS fdsUsername,
                                            a.action_type AS actionType,
                                            a.client_code AS clientCode,
                                            a.action_timestamp AS actionTimeStamp
                                      FROM internal_audit_log a
                                      WHERE a.action_timestamp BETWEEN :from AND :to
                                            AND (COALESCE(:actionTypes, NULL) IS NULL OR a.action_type IN (:actionTypes))
                                            AND (COALESCE(:clientCodes, NULL) IS NULL OR a.client_code IN (:clientCodes))
                                            AND (COALESCE(:usernames, NULL) IS NULL OR a.fds_username IN (:usernames))
                                      ORDER BY a.action_timestamp DESC
                                      ),
               analytics AS (
                                     SELECT\s
                                            (SELECT COUNT(*) FROM filtered_data ) AS count ,
                                            (SELECT actionType FROM filtered_data GROUP BY actionType ORDER BY COUNT(*) DESC LIMIT 1) AS most_used_action,
                                            (SELECT clientCode FROM filtered_data WHERE clientCode IS NOT NULL GROUP BY clientCode ORDER BY COUNT(*) DESC LIMIT 1) AS most_active_client,
                                            (SELECT fdsUsername FROM filtered_data GROUP BY fdsUsername ORDER BY COUNT(*) DESC LIMIT 1) AS most_active_username
                                     )
               SELECT json_build_object(
                                     'records',                                                       \s
                                     COALESCE(
                                              (SELECT json_agg(
                                                    json_build_object(
                                                            'id', id,
                                                            'fdsUsername', fdsUsername,
                                                            'actionType', actionType,
                                                            'clientCode', clientCode,
                                                            'actionTimeStamp', actionTimeStamp
                                                                )
                                                            ) FROM filtered_data),
                                                        '[]'::json
                                              ),
                                                    'analytics',
                                                        (SELECT json_build_object(
                                                            'count', count,
                                                            'mostUsedAction', most_used_action,
                                                            'mostActiveClient', most_active_client,
                                                            'mostActiveUsername', most_active_username
                                                        ) FROM analytics)
                                                ) AS result;
           \s""", nativeQuery = true)
        String fetchAuditInfoJson(
                @Param("from") Timestamp from,
                @Param("to") Timestamp to,
                @Param("actionTypes") List<String> actionTypes,
                @Param("clientCodes") List<String> clientCodes,
                @Param("usernames") List<String> usernames
        );


    @Query(
            value = """
                    SELECT
                        action_type,
                        COUNT(*) AS action_count
                    FROM internal_audit_log
                    WHERE client_code = :clientCode
                      AND action_timestamp BETWEEN :from AND :to
                    GROUP BY action_type
                    ORDER BY action_type;
                    """,
            nativeQuery = true
    )
    List<ActionCountDto>  getClientActionCount(
            @Param("clientCode") String clientCode,
            @Param("from") Timestamp from,
            @Param("to") Timestamp to
    );

    @Query(
            value = """
                    SELECT
                        action_type,
                        COUNT(*) AS count
                    FROM internal_audit_log
                    WHERE fds_username = :username
                      AND action_timestamp BETWEEN :from AND :to
                    GROUP BY action_type
                    ORDER BY action_type;
                    """,
            nativeQuery = true
    )
    List<ActionCountDto> getUserNameActionCount(
            @Param("username") String username,
            @Param("from") Timestamp from,
            @Param("to") Timestamp to
    );

}
