package com.zakaria.inventorymanagement.mapper.impl;

import com.zakaria.inventorymanagement.dto.CategoryDto;
import com.zakaria.inventorymanagement.entity.Category;
import com.zakaria.inventorymanagement.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements Mapper<Category, CategoryDto> {
	private ModelMapper modelMapper;
	
	public CategoryMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public CategoryDto mapTo(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}
	
	@Override
	public Category mapFrom(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}
}
