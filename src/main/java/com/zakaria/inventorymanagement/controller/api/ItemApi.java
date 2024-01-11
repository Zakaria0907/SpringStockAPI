package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.APP_ROOT;

import com.zakaria.inventorymanagement.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("items")
public interface ItemApi {
	
	@PostMapping(value = APP_ROOT + "/items/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Save an item", notes = "This method allows saving or updating an item", response = ItemDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The item was created/updated"),
			@ApiResponse(code = 400, message = "The item is not valid")
	})
	ItemDto save(@RequestBody ItemDto dto);
	
	@GetMapping(value = APP_ROOT + "/items/{idItem}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Search for an item by ID", notes = "This method allows searching for an item by its ID", response = ItemDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The item was found in the database"),
			@ApiResponse(code = 404, message = "No item exists in the database with the provided ID")
	})
	ItemDto findById(@PathVariable("idItem") Integer id);
	
	@GetMapping(value = APP_ROOT + "/items/filter/{codeItem}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Search for an item by CODE", notes = "This method allows searching for an item by its CODE", response = ItemDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The item was found in the database"),
			@ApiResponse(code = 404, message = "No item exists in the database with the provided CODE")
	})
	ItemDto findByCode(@PathVariable("codeItem") String codeItem);
	
	@GetMapping(value = APP_ROOT + "/items/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Return all items list", notes = "This method allows searching and returning the list of all items existing in the database", responseContainer = "List<ItemDto>")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The list of items / An empty list")
	})
	List<ItemDto> findAll();
	
	@GetMapping(value = APP_ROOT + "/items/history/sales/{idItem}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<SaleLineItemDto> findSalesHistory(@PathVariable("idItem") Integer idItem);
	
	@GetMapping(value = APP_ROOT + "/items/history/customerorder/{idItem}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ClientOrderDto> findCustomerOrderHistory(@PathVariable("idItem") Integer idItem);
	
	@GetMapping(value = APP_ROOT + "/items/history/supplierorder/{idItem}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ProviderLineOrderDto> findSupplierOrderHistory(@PathVariable("idItem") Integer idItem);
	
	@GetMapping(value = APP_ROOT + "/items/filter/category/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<ItemDto> findAllItemsByCategoryId(@PathVariable("idCategory") Integer idCategory);
	
	@DeleteMapping(value = APP_ROOT + "/items/delete/{idItem}")
	@ApiOperation(value = "Delete an item", notes = "This method allows deleting an item by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The item was deleted")
	})
	void delete(@PathVariable("idItem") Integer id);
	
}
