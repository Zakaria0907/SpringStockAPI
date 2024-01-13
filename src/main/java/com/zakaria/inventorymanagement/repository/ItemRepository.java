package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // This OPTIONAL annotation is used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public interface ItemRepository extends JpaRepository<Item, Integer> {
	
	Optional<Item> findByItemCode(@Param("itemcode") String itemCode);
	
	List<Item> findAllByCategoryId(@Param("idcategory") Integer categoryId);
	
}
