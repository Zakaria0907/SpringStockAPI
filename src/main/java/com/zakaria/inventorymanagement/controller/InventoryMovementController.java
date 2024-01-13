package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import com.zakaria.inventorymanagement.service.InventoryMovementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Inventory Movement", description = "Inventory Movement API")
@RestController
@RequestMapping(path = "/v1/inventory_movement")
@RequiredArgsConstructor
public class InventoryMovementController{

	private InventoryMovementService inventoryMovementService;

	@Autowired
	public InventoryMovementController(InventoryMovementService inventoryMovementService) {
		this.inventoryMovementService = inventoryMovementService;
	}
	
	@GetMapping(path = "/real_inv/{item-id}")
	public BigDecimal getRealStock(@PathVariable("item-id") Integer articleId) {
		return inventoryMovementService.getRealTimeStock(articleId);
	}
	
	@GetMapping(path = "/inv_movement/{item-id}")
	public List<InventoryMovementDto> getInventoryMovementsByArticle(Integer articleId) {
		return inventoryMovementService.getStockMovements(articleId);
	}
	
	@PostMapping(path = "/entry")
	public InventoryMovementDto entryStock(@RequestBody InventoryMovementDto dto) {
		return inventoryMovementService.enterInventory(dto);
	}
	
	@PostMapping(path = "/exit")
	public InventoryMovementDto exitStock(@RequestBody InventoryMovementDto dto) {
		return inventoryMovementService.exitInventory(dto);
	}
	
	@PostMapping(path = "/positive_correction")
	public InventoryMovementDto positiveCorrectionStock(@RequestBody InventoryMovementDto dto) {
		return inventoryMovementService.positiveStockCorrection(dto);
	}
	
	@PostMapping(path = "/negative_correction")
	public InventoryMovementDto negativeCorrectionStock(@RequestBody InventoryMovementDto dto) {
		return inventoryMovementService.negativeStockCorrection(dto);
	}
}
