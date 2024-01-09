package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.ProviderOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderOrderRepository extends JpaRepository<ProviderOrder, Integer> {
	
	Optional<ProviderOrder> findProviderOrderByCode(String code);
	
	List<ProviderOrder> findAllByProviderId(Integer id);
}
