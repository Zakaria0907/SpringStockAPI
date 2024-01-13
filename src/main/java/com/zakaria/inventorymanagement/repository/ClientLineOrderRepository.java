package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.ClientLineOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientLineOrderRepository extends JpaRepository<ClientLineOrder, Integer> {
	
	List<ClientLineOrder> findAllByClientOrderId(@Param("clientorder_id") Integer clientOrderId);
	List<ClientLineOrder> findAllByItemId(@Param("item_id") Integer itemId);
}
