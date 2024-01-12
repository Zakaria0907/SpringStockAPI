package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.PROVIDER_ORDER_ENDPOINT;
import static com.zakaria.inventorymanagement.utils.Constant.CREATE_SUPPLIER_ORDER_ENDPOINT;
import static com.zakaria.inventorymanagement.utils.Constant.DELETE_SUPPLIER_ORDER_ENDPOINT;
import static com.zakaria.inventorymanagement.utils.Constant.FIND_ALL_SUPPLIER_ORDERS_ENDPOINT;
import static com.zakaria.inventorymanagement.utils.Constant.FIND_SUPPLIER_ORDER_BY_CODE_ENDPOINT;
import static com.zakaria.inventorymanagement.utils.Constant.FIND_SUPPLIER_ORDER_BY_ID_ENDPOINT;

import com.zakaria.inventorymanagement.dto.ProviderOrderDto;
import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("providerorders")
public interface ProviderOrderApi {
	
	@PostMapping(CREATE_SUPPLIER_ORDER_ENDPOINT)
	ProviderOrderDto save(@RequestBody ProviderOrderDto dto);
	
	@PatchMapping(PROVIDER_ORDER_ENDPOINT + "/update/status/{orderId}/{orderStatus}")
	ProviderOrderDto updateOrderStatus(@PathVariable("orderId") Integer orderId, @PathVariable("orderStatus") OrderStatus orderStatus);
	
	@PatchMapping(PROVIDER_ORDER_ENDPOINT + "/update/quantity/{orderId}/{orderLineId}/{quantity}")
	ProviderOrderDto updateOrderQuantity(@PathVariable("orderId") Integer orderId,
	                                     @PathVariable("orderLineId") Integer orderLineId, @PathVariable("quantity") BigDecimal quantity);
	
	@PatchMapping(PROVIDER_ORDER_ENDPOINT + "/update/provider/{orderId}/{providerId}")
	ProviderOrderDto updateProvider(@PathVariable("orderId") Integer orderId, @PathVariable("providerId") Integer providerId);
	
	@PatchMapping(PROVIDER_ORDER_ENDPOINT + "/update/article/{orderId}/{orderLineId}/{articleId}")
	ProviderOrderDto updateItem(@PathVariable("orderId") Integer orderId,
	                            @PathVariable("orderLineId") Integer orderLineId, @PathVariable("articleId") Integer articleId);
	
	@DeleteMapping(PROVIDER_ORDER_ENDPOINT + "/delete/article/{orderId}/{orderLineId}")
	ProviderOrderDto deleteItem(@PathVariable("orderId") Integer orderId, @PathVariable("orderLineId") Integer orderLineId);
	
	@GetMapping(FIND_SUPPLIER_ORDER_BY_ID_ENDPOINT)
	ProviderOrderDto findById(@PathVariable("orderId") Integer orderId);
	
	@GetMapping(FIND_SUPPLIER_ORDER_BY_CODE_ENDPOINT)
	ProviderOrderDto findByCode(@PathVariable("orderCode") String orderCode);
	
	@GetMapping(FIND_ALL_SUPPLIER_ORDERS_ENDPOINT)
	List<ProviderOrderDto> findAll();
	
	@GetMapping(PROVIDER_ORDER_ENDPOINT + "/orderlines/{orderId}")
	List<ProviderLineOrderDto> findAllProviderLineOrdersByProviderOrderId(@PathVariable("orderId") Integer orderId);
	
	@DeleteMapping(DELETE_SUPPLIER_ORDER_ENDPOINT + "/{orderId}")
	void delete(@PathVariable("orderId") Integer orderId);
}
