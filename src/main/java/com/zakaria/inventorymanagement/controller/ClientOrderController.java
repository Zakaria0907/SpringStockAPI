package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.controller.api.ClientOrderApi;
import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.dto.ClientOrderDto;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import com.zakaria.inventorymanagement.service.ClientOrderService;
import com.zakaria.inventorymanagement.service.Impl.ClientOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ClientOrderController implements ClientOrderApi {
	
	private ClientOrderService clientOrderService;
	
	@Autowired
	public ClientOrderController(ClientOrderService clientOrderService) {
		this.clientOrderService = clientOrderService;
	}
	
	@Override
	public ResponseEntity<ClientOrderDto> save(ClientOrderDto dto) {
		ClientOrderDto savedDto = clientOrderService.save(dto);
		return ResponseEntity.ok(savedDto);
	}
	
	@Override
	public ResponseEntity<ClientOrderDto> updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
		ClientOrderDto updatedDto = clientOrderService.updateOrderStatus(orderId, orderStatus);
		return ResponseEntity.ok(updatedDto);
	}
	
	@Override
	public ResponseEntity<ClientOrderDto> updateOrderQuantity(Integer orderId, Integer orderLineId, BigDecimal quantity) {
		ClientOrderDto updatedDto = clientOrderService.updateQuantity(orderId, orderLineId, quantity);
		return ResponseEntity.ok(updatedDto);
	}
	
	@Override
	public ResponseEntity<ClientOrderDto> updateClient(Integer orderId, Integer clientId) {
		ClientOrderDto updatedDto = clientOrderService.updateClient(orderId, clientId);
		return ResponseEntity.ok(updatedDto);
	}
	
	@Override
	public ResponseEntity<ClientOrderDto> updateItem(Integer orderId, Integer orderLineId, Integer articleId) {
		ClientOrderDto updatedDto = clientOrderService.updateItem(orderId, orderLineId, articleId);
		return ResponseEntity.ok(updatedDto);
	}
	
	@Override
	public ResponseEntity<ClientOrderDto> deleteItem(Integer orderId, Integer orderLineId) {
		ClientOrderDto updatedDto = clientOrderService.deleteItem(orderId, orderLineId);
		return ResponseEntity.ok(updatedDto);
	}
	
	@Override
	public ResponseEntity<ClientOrderDto> findById(Integer orderId) {
		ClientOrderDto foundDto = clientOrderService.findById(orderId);
		return ResponseEntity.ok(foundDto);
	}
	
	@Override
	public ResponseEntity<ClientOrderDto> findByCode(String orderCode) {
		ClientOrderDto foundDto = clientOrderService.findByCode(orderCode);
		return ResponseEntity.ok(foundDto);
	}
	
	@Override
	public ResponseEntity<List<ClientOrderDto>> findAll() {
		List<ClientOrderDto> orderDtoList = clientOrderService.findAll();
		return ResponseEntity.ok(orderDtoList);
	}
	
	@Override
	public ResponseEntity<List<ClientLineOrderDto>> findAllLineOrdersByClientOrderId(Integer orderId) {
		List<ClientLineOrderDto> orderLineDtoList = clientOrderService.findAllClientLineOrderByClientOrderId(orderId);
		return ResponseEntity.ok(orderLineDtoList);
	}
	
	@Override
	public ResponseEntity<Void> delete(Integer orderId) {
		clientOrderService.delete(orderId);
		return ResponseEntity.ok().build();
	}
}
