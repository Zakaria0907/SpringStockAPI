package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.APP_ROOT;

import com.zakaria.inventorymanagement.dto.CategoryDto;
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

@Api("categories")
public interface CategoryApi {
	
	@PostMapping(value = APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Save a category", notes = "This method allows you to save or modify a category", response =
			CategoryDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The category object created/modified"),
			@ApiResponse(code = 400, message = "The category object is not valid")
	})
	CategoryDto save(@RequestBody CategoryDto dto);
	
	@GetMapping(value = APP_ROOT + "/categories/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find a category by ID", notes = "This method allows you to find a category by its ID", response =
			CategoryDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The category was found in the database"),
			@ApiResponse(code = 404, message = "No category exists in the database with the provided ID")
	})
	CategoryDto findById(@PathVariable("idCategory") Integer idCategory);
	
	@GetMapping(value = APP_ROOT + "/categories/filter/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Find a category by CODE", notes = "This method allows you to find a category by its CODE", response =
			CategoryDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The category was found in the database"),
			@ApiResponse(code = 404, message = "No category exists in the database with the provided CODE")
	})
	CategoryDto findByCode(@PathVariable("codeCategory") String codeCategory);
	
	@GetMapping(value = APP_ROOT + "/categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get the list of categories", notes = "This method allows you to search and return the list of categories that exist "
			+ "in the database", responseContainer = "List<CategoryDto>")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The list of categories / An empty list")
	})
	List<CategoryDto> findAll();
	
	@DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
	@ApiOperation(value = "Delete a category", notes = "This method allows you to delete a category by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The category was deleted")
	})
	void delete(@PathVariable("idCategory") Integer id);
}
