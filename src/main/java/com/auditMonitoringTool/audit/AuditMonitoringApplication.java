package com.auditMonitoringTool.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class AuditMonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditMonitoringApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(DataSource dataSource) {
		return args -> {
			try (Connection connection = dataSource.getConnection()) {
				if (connection.isValid(1000)) {
					System.out.println("DATABASE CONNECTION SUCCESSFUL: " + connection.getMetaData().getURL());
				}
			} catch (Exception e) {
				System.out.println("DATABASE CONNECTION FAILED: " + e.getMessage());
			}
		};
	}

}