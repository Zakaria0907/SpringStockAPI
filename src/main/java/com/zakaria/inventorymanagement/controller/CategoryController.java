package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.CategoryDto;
import com.zakaria.inventorymanagement.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/category")
@RequiredArgsConstructor
public class CategoryController {
	
	private CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping(path = "/save")
	public CategoryDto save(@RequestBody CategoryDto dto) {
		return categoryService.save(dto);
	}
	
	@GetMapping(path = "/{category-id}")
	public CategoryDto findById( @PathVariable("category-id") Integer idCategory) {
		return categoryService.findById(idCategory);
	}

	@GetMapping(path = "/{category-code}")
	public CategoryDto findByCode(@PathVariable("category-code") String codeCategory) {
		return categoryService.findByCode(codeCategory);
	}
	
	@GetMapping(path = "/all")
	public List<CategoryDto> findAll() {
		return categoryService.findAll();
	}
	
	@DeleteMapping(path = "/delete/{category-id}")
	public void delete(@PathVariable("category-id") Integer id) {
		categoryService.delete(id);
	}
}
