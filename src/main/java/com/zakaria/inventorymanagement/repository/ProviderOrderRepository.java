package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.ProviderOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProviderOrderRepository extends JpaRepository<ProviderOrder, Integer> {
	
	Optional<ProviderOrder> findProviderOrderByCode(@Param("code") String code);
	
	List<ProviderOrder> findAllByProviderId(@Param("provider_id")  Integer id);
}
