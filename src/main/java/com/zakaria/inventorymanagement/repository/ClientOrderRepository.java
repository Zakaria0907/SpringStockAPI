package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientOrderRepository extends JpaRepository<ClientOrder, Integer> {
	
	Optional<ClientOrder> findClientOrderByCode(@Param("code")String code);
	
	List<ClientOrder> findAllByClientId(@Param("client_id") Integer clientId);
}
