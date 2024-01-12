package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.controller.api.ProviderOrderApi;
import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import com.zakaria.inventorymanagement.dto.ProviderOrderDto;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import com.zakaria.inventorymanagement.service.ProviderOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProviderOrderController implements ProviderOrderApi {
	
	private ProviderOrderService providerOrderService;
	
	@Autowired
	public ProviderOrderController(ProviderOrderService providerOrderService) {
		this.providerOrderService = providerOrderService;
	}
	
	@Override
	public ProviderOrderDto save(ProviderOrderDto dto) {
		return providerOrderService.save(dto);
	}
	
	@Override
	public ProviderOrderDto updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
		return providerOrderService.updateOrderStatus(orderId, orderStatus);
	}
	
	@Override
	public ProviderOrderDto updateOrderQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity) {
		return providerOrderService.updateQuantity(orderId, orderLineId, quantity);
	}
	
	@Override
	public ProviderOrderDto updateProvider(Integer orderId, Integer providerId) {
		return providerOrderService.updateProvider(orderId, providerId);
	}
	
	@Override
	public ProviderOrderDto updateItem(Integer orderId, Integer orderLineId, Integer articleId) {
		return providerOrderService.updateItem(orderId, orderLineId, articleId);
	}
	
	@Override
	public ProviderOrderDto deleteItem(Integer orderId, Integer orderLineId) {
		return providerOrderService.deleteItem(orderId, orderLineId);
	}
	
	@Override
	public ProviderOrderDto findById(Integer orderId) {
		return providerOrderService.findById(orderId);
	}
	
	@Override
	public ProviderOrderDto findByCode(String orderCode) {
		return providerOrderService.findByCode(orderCode);
	}
	
	@Override
	public List<ProviderOrderDto> findAll() {
		return providerOrderService.findAll();
	}
	
	@Override
	public List<ProviderLineOrderDto> findAllProviderLineOrdersByProviderOrderId(Integer orderId) {
		return providerOrderService.findAllProviderLineOrdersByProviderOrderId(orderId);
	}
	
	@Override
	public void delete(Integer orderId) {
		providerOrderService.delete(orderId);
	}
}
