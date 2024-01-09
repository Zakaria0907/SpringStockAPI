package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
