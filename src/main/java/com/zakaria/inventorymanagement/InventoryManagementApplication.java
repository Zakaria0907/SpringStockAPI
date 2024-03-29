package com.zakaria.inventorymanagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication
@EnableJpaAuditing
public class InventoryManagementApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementApplication.class, args);
		System.out.println("<== Inventory Management System: Started ==>");
	}
	
	
}
