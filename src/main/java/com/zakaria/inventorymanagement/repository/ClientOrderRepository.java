package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {
	
	Optional<ClientOrder> findClientOrderByCode(String code);
	
	List<ClientOrder> findAllByClientId(Integer clientId);
}
