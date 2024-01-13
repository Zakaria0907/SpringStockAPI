package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.SaleLineItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SaleLineItemRepository extends JpaRepository<SaleLineItem, Integer> {
	
	List<SaleLineItem> findAllByItemId(@Param("item_id") Integer itemId);
	
	List<SaleLineItem> findAllBySaleId(@Param("sale_id") Integer saleId);
}
