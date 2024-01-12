package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.APP_ROOT;

import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("inventorymovement")
public interface InventoryMovementApi {
	
	@GetMapping(APP_ROOT + "/realstock/{articleId}")
	BigDecimal getRealStock(@PathVariable("articleId") Integer articleId);
	
	@GetMapping(APP_ROOT + "/filter/article/{articleId}")
	List<InventoryMovementDto> getInventoryMovementsByArticle(@PathVariable("articleId") Integer articleId);
	
	@PostMapping(APP_ROOT + "/entry")
	InventoryMovementDto entryStock(@RequestBody InventoryMovementDto dto);
	
	@PostMapping(APP_ROOT + "/exit")
	InventoryMovementDto exitStock(@RequestBody InventoryMovementDto dto);
	
	@PostMapping(APP_ROOT + "/correctionpos")
	InventoryMovementDto positiveCorrectionStock(@RequestBody InventoryMovementDto dto);
	
	@PostMapping(APP_ROOT + "/correctionneg")
	InventoryMovementDto negativeCorrectionStock(@RequestBody InventoryMovementDto dto);
	
}
