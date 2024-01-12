//package com.zakaria.inventorymanagement.config;
//
//import com.zakaria.inventorymanagement.dto.AddressDto;
//import com.zakaria.inventorymanagement.dto.CompanyUserDto;
//import com.zakaria.inventorymanagement.entity.CompanyUser;
//import com.zakaria.inventorymanagement.repository.CompanyUserRepository;
//import com.zakaria.inventorymanagement.service.Impl.CompanyUserServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//
//@Component
//public class CommandLineAppStartupRunner implements CommandLineRunner {
//	@Autowired
//	CompanyUserServiceImpl userService;
//
//	@Override
//	public void run(String... args) throws Exception {
//		CompanyUserDto adminUser = new CompanyUserDto();
//		adminUser.setEmail("admin@admin.com");
//		adminUser.setPassword("admin"); // Consider using a stronger, hashed password in production
//		adminUser.setFirstname("Admin");
//		adminUser.setLastname("User");
//		adminUser.setDateOfBirth(Instant.now()); // Set an appropriate date
//		// Set the address. Make sure it's a valid address object that passes AddressValidator
//		AddressDto address = new AddressDto();
//		address.setAddress1("Admin Address 1");
//		address.setCity("Admin City");
//		address.setCountry("Admin Country");
//		address.setZipCode("12345");
//		adminUser.setAddress(address);
//
//		// Save the admin user
//		userService.save(adminUser);
//	}
//
//}
