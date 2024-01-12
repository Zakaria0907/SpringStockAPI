package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.APP_ROOT;

import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.dto.ClientOrderDto;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("clientorders")
public interface ClientOrderApi {
	
	@PostMapping(APP_ROOT + "/clientorders/create")
	ResponseEntity<ClientOrderDto> save(@RequestBody ClientOrderDto dto);
	
	@PatchMapping(APP_ROOT + "/clientorders/update/status/{orderId}/{orderStatus}")
	ResponseEntity<ClientOrderDto> updateOrderStatus(@PathVariable("orderId") Integer orderId, @PathVariable("orderStatus") OrderStatus orderStatus);
	
	@PatchMapping(APP_ROOT + "/clientorders/update/quantity/{orderId}/{orderLineId}/{quantity}")
	ResponseEntity<ClientOrderDto> updateOrderQuantity(@PathVariable("orderId") Integer orderId,
	                                                           @PathVariable("orderLineId") Integer orderLineId, @PathVariable("quantity") BigDecimal quantity);
	
	@PatchMapping(APP_ROOT + "/clientorders/update/client/{orderId}/{clientId}")
	ResponseEntity<ClientOrderDto> updateClient(@PathVariable("orderId") Integer orderId, @PathVariable("clientId") Integer clientId);
	
	@PatchMapping(APP_ROOT + "/clientorders/update/article/{orderId}/{orderLineId}/{articleId}")
	ResponseEntity<ClientOrderDto> updateItem(@PathVariable("orderId") Integer orderId,
	                                                          @PathVariable("orderLineId") Integer orderLineId, @PathVariable("articleId") Integer articleId);
	
	@DeleteMapping(APP_ROOT + "/clientorders/delete/article/{orderId}/{orderLineId}")
	ResponseEntity<ClientOrderDto> deleteItem(@PathVariable("orderId") Integer orderId, @PathVariable("orderLineId") Integer orderLineId);
	
	@GetMapping(APP_ROOT + "/clientorders/{orderId}")
	ResponseEntity<ClientOrderDto> findById(@PathVariable Integer orderId);
	
	@GetMapping(APP_ROOT + "/clientorders/filter/{orderCode}")
	ResponseEntity<ClientOrderDto> findByCode(@PathVariable("orderCode") String orderCode);
	
	@GetMapping(APP_ROOT + "/clientorders/all")
	ResponseEntity<List<ClientOrderDto>> findAll();
	
	@GetMapping(APP_ROOT + "/clientorders/lineorders/{orderId}")
	ResponseEntity<List<ClientLineOrderDto>> findAllLineOrdersByClientOrderId(@PathVariable("orderId") Integer orderId);
	
	@DeleteMapping(APP_ROOT + "/clientorders/delete/{orderId}")
	ResponseEntity<Void> delete(@PathVariable("orderId") Integer orderId);
}
