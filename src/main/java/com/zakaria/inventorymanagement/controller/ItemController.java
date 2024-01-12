package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.controller.api.ItemApi;
import com.zakaria.inventorymanagement.dto.ItemDto;
import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import com.zakaria.inventorymanagement.dto.SaleLineItemDto;
import com.zakaria.inventorymanagement.service.ItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController implements ItemApi {
	
	private ItemService itemService;
	
	@Autowired
	public ItemController(
			ItemService itemService
	) {
		this.itemService = itemService;
	}
	
	@Override
	public ItemDto save(ItemDto dto) {
		return itemService.save(dto);
	}
	
	@Override
	public ItemDto findById(Integer id) {
		return itemService.findById(id);
	}
	
	@Override
	public ItemDto findByCode(String codeItem) {
		return itemService.findByItemCode(codeItem);
	}
	
	@Override
	public List<ItemDto> findAll() {
		return itemService.findAll();
	}
	
	@Override
	public List<SaleLineItemDto> findSalesHistory(Integer idItem) { // Changed from LigneVenteDto
		return itemService.findSaleHistory(idItem);
	}
	
	@Override
	public List<ClientLineOrderDto> findCustomerOrderHistory(Integer idItem) { // Changed from LigneCommandeClientDto
		return itemService.findClientOrderHistory(idItem);
	}
	
	@Override
	public List<ProviderLineOrderDto> findSupplierOrderHistory(Integer idItem) { // Changed from LigneCommandeFournisseurDto
		return itemService.findProviderOrderHistory(idItem);
	}
	
	@Override
	public List<ItemDto> findAllItemsByCategoryId(Integer idCategory) {
		return itemService.findAllItemsByCategoryId(idCategory);
	}
	
	@Override
	public void delete(Integer id) {
		itemService.delete(id);
	}
}
