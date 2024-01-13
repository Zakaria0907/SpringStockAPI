package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Optional<Category> findCategoryByCode(@Param("code") String code);

}
