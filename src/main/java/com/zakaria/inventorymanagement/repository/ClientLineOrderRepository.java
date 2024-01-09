package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.ClientLineOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientLineOrderRepository extends JpaRepository<ClientLineOrder, Integer> {
	List<ClientLineOrder> findAllByClientOrderId(Integer clientOrderId);
	List<ClientLineOrder> findAllByItemId(Integer itemId);
}
