package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Integer> {
	
	@Query("SELECT cu FROM CompanyUser cu WHERE cu.email = :email")
	Optional<CompanyUser> findCompanyUserByEmail(@Param("email") String email);
}
