package com.hackacode.clinica;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Medpoint Clinic",
		version = "1.0.0",
		description = "APIs Swagger Microservicio MedPoint"
))
@SpringBootApplication

public class ClinicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicaApplication.class, args);
	}

	@PostConstruct
	public void logEnvVariables() {
		System.out.println("JWT_SECRET: " + System.getenv("JWT_SECRET"));
		System.out.println("DB URL: " + System.getenv("DB_URL"));
		System.out.println("DB NAME: " + System.getenv("DB_NAME"));
		System.out.println("DB USERNAME: " + System.getenv("DB_USERNAME"));
		System.out.println("DB PASSWORD: " + System.getenv("DB_PASSWORD"));
		System.out.println("DB PORT: " + System.getenv("DB_PORT"));
	}
}
