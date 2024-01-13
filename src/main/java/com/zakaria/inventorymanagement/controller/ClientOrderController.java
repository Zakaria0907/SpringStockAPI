package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.dto.ClientOrderDto;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import com.zakaria.inventorymanagement.service.ClientOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Client Order", description = "ClientOrder API")
@RestController
@RequestMapping(path = "/v1/client/order")
@RequiredArgsConstructor
public class ClientOrderController {

	private ClientOrderService clientOrderService;

	@Autowired
	public ClientOrderController(ClientOrderService clientOrderService) {
		this.clientOrderService = clientOrderService;
	}
	
	@PostMapping(path = "/save")
	public ResponseEntity<ClientOrderDto> save(@RequestBody ClientOrderDto dto) {
		ClientOrderDto savedDto = clientOrderService.save(dto);
		return ResponseEntity.ok(savedDto);
	}
	
	@PostMapping(path = "/update/status/{order-id}/{order-status}")
	public ResponseEntity<ClientOrderDto> updateOrderStatus(@PathVariable("order-id") Integer orderId,
	                                                        @PathVariable("order-status") OrderStatus orderStatus) {
		ClientOrderDto updatedDto = clientOrderService.updateOrderStatus(orderId, orderStatus);
		return ResponseEntity.ok(updatedDto);
	}
	
	@PostMapping(path = "/update/quantity/{order-id}/{line-order-id}/{order-quantity}")
	public ResponseEntity<ClientOrderDto> updateOrderQuantity(@PathVariable("order-id") Integer orderId,
	                                                          @PathVariable("line-order-id") Integer orderLineId,
	                                                          @PathVariable("order-quantity") BigDecimal quantity) {
		ClientOrderDto updatedDto = clientOrderService.updateQuantity(orderId, orderLineId, quantity);
		return ResponseEntity.ok(updatedDto);
	}
	
	@PostMapping(path = "/update/client/{order-id}/{client-id}")
	public ResponseEntity<ClientOrderDto> updateClient(@PathVariable("order-id") Integer orderId,
	                                                   @PathVariable("client-id") Integer clientId) {
		ClientOrderDto updatedDto = clientOrderService.updateClient(orderId, clientId);
		return ResponseEntity.ok(updatedDto);
	}
	
	@PostMapping(path = "/update/item/{order-id}/{line-order-id}/{item-id}")
	public ResponseEntity<ClientOrderDto> updateItem(@PathVariable("order-id") Integer orderId,
	                                                 @PathVariable("line-order-id") Integer orderLineId,
	                                                 @PathVariable("item-id") Integer articleId) {
		ClientOrderDto updatedDto = clientOrderService.updateItem(orderId, orderLineId, articleId);
		return ResponseEntity.ok(updatedDto);
	}
	
	@PostMapping(path = "/delete/item/{order-id}/{line-order-id}")
	public ResponseEntity<ClientOrderDto> deleteItem(@PathVariable("order-id") Integer orderId,
	                                                 @PathVariable("line-order-id") Integer orderLineId) {
		ClientOrderDto updatedDto = clientOrderService.deleteItem(orderId, orderLineId);
		return ResponseEntity.ok(updatedDto);
	}
	
	@GetMapping(path = "/id/{order-id}")
	public ResponseEntity<ClientOrderDto> findById(@PathVariable("order-id") Integer orderId) {
		ClientOrderDto foundDto = clientOrderService.findById(orderId);
		return ResponseEntity.ok(foundDto);
	}
	
	@GetMapping(path = "/code/{order-code}")
	public ResponseEntity<ClientOrderDto> findByCode(@PathVariable("order-code") String orderCode) {
		ClientOrderDto foundDto = clientOrderService.findByCode(orderCode);
		return ResponseEntity.ok(foundDto);
	}
	
	@GetMapping(path = "/all")
	public ResponseEntity<List<ClientOrderDto>> findAll() {
		List<ClientOrderDto> orderDtoList = clientOrderService.findAll();
		return ResponseEntity.ok(orderDtoList);
	}
	
	@GetMapping(path = "/all/{order-id}")
	public ResponseEntity<List<ClientLineOrderDto>> findAllLineOrdersByClientOrderId(@PathVariable("order-id") Integer orderId) {
		List<ClientLineOrderDto> orderLineDtoList = clientOrderService.findAllClientLineOrderByClientOrderId(orderId);
		return ResponseEntity.ok(orderLineDtoList);
	}
	
	@DeleteMapping(path = "/delete/{order-id}")
	public ResponseEntity<Void> delete(@PathVariable("order-id") Integer orderId) {
		clientOrderService.delete(orderId);
		return ResponseEntity.ok().build();
	}
}
