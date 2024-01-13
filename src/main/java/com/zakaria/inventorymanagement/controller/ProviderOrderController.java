package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import com.zakaria.inventorymanagement.dto.ProviderOrderDto;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import com.zakaria.inventorymanagement.service.ProviderOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/provider/order")
@RequiredArgsConstructor
public class ProviderOrderController  {

	private ProviderOrderService providerOrderService;

	@Autowired
	public ProviderOrderController(ProviderOrderService providerOrderService) {
		this.providerOrderService = providerOrderService;
	}
	
	@PostMapping(path = "/save")
	public ProviderOrderDto save(@RequestBody ProviderOrderDto dto) {
		return providerOrderService.save(dto);
	}
	
	@PostMapping(path = "/update/status/{order-id}/{order-status}")
	public ProviderOrderDto updateOrderStatus(@PathVariable("order-id") Integer orderId,
	                                          @PathVariable("order-status") OrderStatus orderStatus) {
		return providerOrderService.updateOrderStatus(orderId, orderStatus);
	}
	
	@PostMapping(path = "/update/quantity/{order-id}/{line-order-id}/{order-quantity}")
	public ProviderOrderDto updateOrderQuantity(@PathVariable("order-id") Integer orderId,
	                                            @PathVariable("line-order-id") Integer orderLineId,
	                                            @PathVariable("order-quantity") BigDecimal quantity) {
		return providerOrderService.updateQuantity(orderId, orderLineId, quantity);
	}
	
	@PostMapping(path = "/update/client/{order-id}/{client-id}")
	public ProviderOrderDto updateProvider(@PathVariable("order-id") Integer orderId,
	                                       @PathVariable("client-id") Integer providerId) {
		return providerOrderService.updateProvider(orderId, providerId);
	}
	
	@PostMapping(path = "/update/item/{order-id}/{line-order-id}/{item-id}")
	public ProviderOrderDto updateItem(@PathVariable("order-id") Integer orderId,
	                                   @PathVariable("line-order-id") Integer orderLineId,
	                                   @PathVariable("item-id") Integer articleId) {
		return providerOrderService.updateItem(orderId, orderLineId, articleId);
	}
	
	@PostMapping(path = "/delete/item/{order-id}/{line-order-id}")
	public ProviderOrderDto deleteItem(@PathVariable("order-id") Integer orderId,
	                                   @PathVariable("line-order-id") Integer orderLineId) {
		return providerOrderService.deleteItem(orderId, orderLineId);
	}
	
	@GetMapping(path = "/id/{order-id}")
	public ProviderOrderDto findById(@PathVariable("order-id") Integer orderId) {
		return providerOrderService.findById(orderId);
	}
	
	@GetMapping(path = "/code/{order-code}")
	public ProviderOrderDto findByCode(@PathVariable("order-code") String orderCode) {
		return providerOrderService.findByCode(orderCode);
	}
	
	@GetMapping(path = "/all")
	public List<ProviderOrderDto> findAll() {
		return providerOrderService.findAll();
	}
	
	@GetMapping(path = "/all/{order-id}")
	public List<ProviderLineOrderDto> findAllProviderLineOrdersByProviderOrderId(@PathVariable("order-id") Integer orderId) {
		return providerOrderService.findAllProviderLineOrdersByProviderOrderId(orderId);
	}
	
	@DeleteMapping(path = "/delete/{order-id}")
	public void delete(Integer orderId) {
		providerOrderService.delete(orderId);
	}
}
