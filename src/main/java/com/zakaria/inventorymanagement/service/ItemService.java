package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.ItemDto;
import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import com.zakaria.inventorymanagement.dto.SaleLineItemDto;
import java.util.List;

public interface ItemService {
	
	ItemDto save(ItemDto itemDto);
	
	ItemDto findById(Integer id);
	
	ItemDto findByItemCode(String itemCode);
	
	List<ItemDto> findAll();
	
	List<SaleLineItemDto> findSaleHistory(Integer itemId);
	
	List<ClientLineOrderDto> findClientOrderHistory(Integer itemId);
	
	List<ProviderLineOrderDto> findProviderOrderHistory(Integer itemId);
	
	List<ItemDto> findAllItemsByCategoryId(Integer categoryId);
	
	void delete(Integer id);
	
}
