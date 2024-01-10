package com.zakaria.inventorymanagement.repository;

import com.zakaria.inventorymanagement.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
