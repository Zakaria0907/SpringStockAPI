package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.CategoryDto;
import com.zakaria.inventorymanagement.entity.Item;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.mapper.impl.CategoryMapperImpl;
import com.zakaria.inventorymanagement.repository.CategoryRepository;
import com.zakaria.inventorymanagement.repository.ItemRepository;
import com.zakaria.inventorymanagement.service.CategoryService;
import com.zakaria.inventorymanagement.validator.CategoryValidator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	private CategoryRepository categoryRepository;
	private ItemRepository itemRepository;
	
	private CategoryMapperImpl categoryMapper;
	
	@Autowired
	public CategoryServiceImpl(
			CategoryRepository categoryRepository,
			ItemRepository itemRepository,
			CategoryMapperImpl categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.itemRepository = itemRepository;
		this.categoryMapper = categoryMapper;
	}
	
	@Override
	public CategoryDto save(CategoryDto categoryDto) {
		List<String> errors = CategoryValidator.validateCategory(categoryDto);
		if (!errors.isEmpty()) {
			log.error("Category is not valid {}", categoryDto);
			throw new InvalidEntityException("Invalid category", ErrorCodes.CATEGORY_NOT_VALID, errors);
		}
		
		return categoryMapper.mapTo(
				categoryRepository.save(
						categoryMapper.mapFrom(categoryDto)
				)
		);
	}
	
	@Override
	public CategoryDto findById(Integer id) {
		if (id == null) {
			log.error("Category ID is null");
			return null;
		}
		
		return categoryRepository.findById(id).map(categoryMapper::mapTo).orElseThrow(() ->
				new EntityNotFoundException(
						"No category with ID = " + id + " found in DB",
						ErrorCodes.CATEGORY_NOT_FOUND)
		);
	}
	
	@Override
	public CategoryDto findByCode(String code) {
		if (!StringUtils.hasLength(code)) {
			log.error("Category CODE is null");
			return null;
		}
		
		return categoryRepository.findCategoryByCode(code)
				.map(categoryMapper::mapTo)
				.orElseThrow(() ->
						new EntityNotFoundException(
								"No category with code = " + code + " found in DB",
								ErrorCodes.CATEGORY_NOT_FOUND)
				);
	}
	
	@Override
	public List<CategoryDto> findAll() {
		return categoryRepository.findAll().stream()
				.map(categoryMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("Category ID is null");
			return;
		}
		List<Item> items = itemRepository.findAllByCategoryId(id);
		if (!items.isEmpty()) {
			throw new InvalidOperationException("Cannot delete a category that is already in use",
					ErrorCodes.CATEGORY_ALREADY_IN_USE);
		}
		categoryRepository.deleteById(id);
	}
	
	// Additional methods go here, ensure you replace the DTOs, entities, and repository methods to match those available in your project.
}
