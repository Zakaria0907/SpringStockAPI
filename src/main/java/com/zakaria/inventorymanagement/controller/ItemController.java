package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.ItemDto;
import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import com.zakaria.inventorymanagement.dto.SaleLineItemDto;
import com.zakaria.inventorymanagement.service.ItemService;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Item", description = "Item API")
@RestController
@RequestMapping(path = "/v1/item")
@RequiredArgsConstructor
public class ItemController {

	private ItemService itemService;

	@Autowired
	public ItemController(
			ItemService itemService
	) {
		this.itemService = itemService;
	}
	
	@PostMapping(path = "/save")
	public ItemDto save(@RequestBody ItemDto dto) {
		return itemService.save(dto);
	}
	
	@GetMapping(path = "/id/{item-id}")
	public ItemDto findById(@PathVariable("item-id") Integer id) {
		return itemService.findById(id);
	}
	
	@GetMapping(path = "/code/{item-code}")
	public ItemDto findByCode(@PathVariable("item-code") String codeItem) {
		return itemService.findByItemCode(codeItem);
	}
	
	@GetMapping(path = "/all")
	public List<ItemDto> findAll() {
		return itemService.findAll();
	}
	
	@GetMapping(path = "/sale_history/{item-id}")
	public List<SaleLineItemDto> findSalesHistory(@PathVariable("item-id") Integer idItem) { // Changed from LigneVenteDto
		return itemService.findSaleHistory(idItem);
	}
	
	@GetMapping(path = "/customer/order_history/{item-id}")
	public List<ClientLineOrderDto> findCustomerOrderHistory(@PathVariable("item-id") Integer idItem) { // Changed from LigneCommandeClientDto
		return itemService.findClientOrderHistory(idItem);
	}
	
	@GetMapping(path = "/provider/order_history/{item-id}")
	public List<ProviderLineOrderDto> findSupplierOrderHistory(@PathVariable("item-id") Integer idItem) { // Changed from LigneCommandeFournisseurDto
		return itemService.findProviderOrderHistory(idItem);
	}
	
	@GetMapping(path = "/category/{category-id}")
	public List<ItemDto> findAllItemsByCategoryId(@PathVariable("category-id") Integer idCategory) {
		return itemService.findAllItemsByCategoryId(idCategory);
	}
	
	@DeleteMapping(path = "/delete/{item-id}")
	public void delete(@PathVariable("item-id") Integer id) {
		itemService.delete(id);
	}
}
