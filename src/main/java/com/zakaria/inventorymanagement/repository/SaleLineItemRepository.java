package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.SaleLineItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleLineItemRepository extends JpaRepository<SaleLineItem, Integer> {
	
	List<SaleLineItem> findAllByItemId(Integer itemId);
	
	List<SaleLineItem> findAllBySaleId(Integer saleId);
}
