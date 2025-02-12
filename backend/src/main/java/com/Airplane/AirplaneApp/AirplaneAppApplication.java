package com.Airplane.AirplaneApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Airplane Management API",
				version = "1.0",
				description = "API documentation for Airplane Management System"
		)
)
public class AirplaneAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(AirplaneAppApplication.class, args);
	}
}