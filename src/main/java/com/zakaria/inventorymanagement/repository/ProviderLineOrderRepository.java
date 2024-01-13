package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.ProviderLineOrder;
import com.zakaria.inventorymanagement.entity.ProviderLineOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProviderLineOrderRepository extends JpaRepository<ProviderLineOrder, Integer> {
	
	List<ProviderLineOrder> findAllByProviderOrderId(@Param("providerorder_id") Integer idProviderOrder);
	
	List<ProviderLineOrder> findAllByItemId(@Param("product_id") Integer itemId);
}
