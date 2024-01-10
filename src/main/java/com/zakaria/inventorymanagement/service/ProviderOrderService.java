package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.ProviderOrderDto;
import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import java.math.BigDecimal;
import java.util.List;

public interface ProviderOrderService {
	
	ProviderOrderDto save(ProviderOrderDto providerOrderDto);
	
	ProviderOrderDto updateOrderStatus(Integer idOrder, OrderStatus orderStatus);
	
	ProviderOrderDto updateQuantity(Integer idOrder, Integer idLineOrder, BigDecimal quantity);
	
	ProviderOrderDto updateProvider(Integer idOrder, Integer idProvider);
	
	ProviderOrderDto updateItem(Integer idOrder, Integer idLineOrder, Integer idItem);
	
	// Delete item ==> delete ProviderLineOrder
	ProviderOrderDto deleteItem(Integer idOrder, Integer idLineOrder);
	
	ProviderOrderDto findById(Integer id);
	
	ProviderOrderDto findByCode(String code);
	
	List<ProviderOrderDto> findAll();
	
	List<ProviderLineOrderDto> findAllProviderLineOrdersByProviderOrderId(Integer idOrder);
	
	void delete(Integer id);
	
}
