package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Integer> {
	
	@Query("SELECT sum(im.quantity) FROM InventoryMovement im WHERE im.item.id = :ItemId")
	BigDecimal realInventoryStock(@Param("ItemId") Integer ItemId);
	
	List<InventoryMovement> findAllByItemId(@Param("ItemId") Integer itemId);
	
}
