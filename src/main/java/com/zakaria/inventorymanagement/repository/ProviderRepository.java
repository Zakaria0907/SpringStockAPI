package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {
}
