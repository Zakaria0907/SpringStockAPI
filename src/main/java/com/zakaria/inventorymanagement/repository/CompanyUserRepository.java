package com.zakaria.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyUserRepository extends JpaRepository<CompanyUserRepository, Integer> {
	
	@Query("SELECT cu FROM CompanyUser cu WHERE cu.email = :email")
	Optional<CompanyUserRepository> findCompanyUserByEmail(@Param("email") String email);
}
