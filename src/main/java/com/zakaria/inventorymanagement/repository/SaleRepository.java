package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.Sale;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
	
	Optional<Sale> findSaleByCode(String code);
}
