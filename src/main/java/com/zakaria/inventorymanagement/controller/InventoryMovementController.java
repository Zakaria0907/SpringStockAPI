//package com.zakaria.inventorymanagement.controller;
//
//import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
//import com.zakaria.inventorymanagement.service.InventoryMovementService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//
//@RestController
//public class InventoryMovementController{
//
//	private InventoryMovementService inventoryMovementService;
//
//	@Autowired
//	public InventoryMovementController(InventoryMovementService inventoryMovementService) {
//		this.inventoryMovementService = inventoryMovementService;
//	}
//
//	@Override
//	public BigDecimal getRealStock(Integer articleId) {
//		return inventoryMovementService.getRealTimeStock(articleId);
//	}
//
//	@Override
//	public List<InventoryMovementDto> getInventoryMovementsByArticle(Integer articleId) {
//		return inventoryMovementService.getStockMovements(articleId);
//	}
//
//	@Override
//	public InventoryMovementDto entryStock(InventoryMovementDto dto) {
//		return inventoryMovementService.enterInventory(dto);
//	}
//
//	@Override
//	public InventoryMovementDto exitStock(InventoryMovementDto dto) {
//		return inventoryMovementService.exitInventory(dto);
//	}
//
//	@Override
//	public InventoryMovementDto positiveCorrectionStock(InventoryMovementDto dto) {
//		return inventoryMovementService.positiveStockCorrection(dto);
//	}
//
//	@Override
//	public InventoryMovementDto negativeCorrectionStock(InventoryMovementDto dto) {
//		return inventoryMovementService.negativeStockCorrection(dto);
//	}
//}
