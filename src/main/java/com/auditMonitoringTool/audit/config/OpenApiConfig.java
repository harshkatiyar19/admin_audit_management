package com.auditMonitoringTool.audit.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Admin Audit Log API",
                version = "1.0.0",
                description = "APIs for querying audit logs, analytics, and summaries"
        )
)
public class OpenApiConfig {
}
