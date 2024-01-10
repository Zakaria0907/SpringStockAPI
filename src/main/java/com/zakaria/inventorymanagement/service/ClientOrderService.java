package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.ClientOrderDto;
import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import java.math.BigDecimal;
import java.util.List;

public interface ClientOrderService {
	
	ClientOrderDto save(ClientOrderDto clientOrderDto);
	
	ClientOrderDto updateOrderStatus(Integer idOrder, OrderStatus orderStatus);
	
	ClientOrderDto updateQuantity(Integer idOrder, Integer idLineOrder, BigDecimal quantity);
	
	ClientOrderDto updateClient(Integer idOrder, Integer idClient);
	
	ClientOrderDto updateItem(Integer idOrder, Integer idLineOrder, Integer newItemId);
	
	// Delete item ==> delete ClientLineOrder
	ClientOrderDto deleteItem(Integer idOrder, Integer idLineOrder);
	
	ClientOrderDto findById(Integer id);
	
	ClientOrderDto findByCode(String code);
	
	List<ClientOrderDto> findAll();
	
	List<ClientLineOrderDto> findAllClientLineOrderByClientOrderId(Integer idOrder);
	
	void delete(Integer id);
	
}
