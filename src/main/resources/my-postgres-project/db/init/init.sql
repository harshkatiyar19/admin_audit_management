CREATE TYPE admin_action AS ENUM (
    'ALERT_JOB',
    'SYNC_PRICING_CONFIG',
    'SYNC_SPECIAL_EVENT_CATEGORIES',
    'INSTRUCTION_SHEET_UPLOAD',
    'ADD_NEW_CLIENT',
    'SYNC_ROOMS',
    'SYNC_FORECAST_GROUP',
    'SYNC_ROLES_PERMISSIONS',
    'SYNC_USERS',
    'DYNAMIC',
    'GRANT_CORE_ADMIN_ACCESS',
    'REVOKE_CORE_ADMIN_ACCESS'
);

CREATE TABLE internal_audit_log (
    id BIGSERIAL PRIMARY KEY,
    fds_user_id VARCHAR(255) NOT NULL,
    fds_username VARCHAR(50) NOT NULL,
    action_type admin_action NOT NULL,
    client_code VARCHAR(255) NOT NULL,
    action_timestamp TIMESTAMP NOT NULL
);

COPY internal_audit_log (
    id,
    fds_user_id,
    fds_username,
    action_type,
    client_code,
    action_timestamp
)
FROM '/docker-entrypoint-initdb.d/internal_audit_log.csv'
DELIMITER ','
CSV HEADER;