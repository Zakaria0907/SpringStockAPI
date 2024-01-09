package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.ProviderLineOrder;
import com.zakaria.inventorymanagement.entity.ProviderLineOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderLineOrderRepository extends JpaRepository<ProviderLineOrder, Integer> {
	
	List<ProviderLineOrder> findAllByProviderOrderId(Integer idProviderOrder);
	
	List<ProviderLineOrder> findAllByItemId(Integer itemId);
}
