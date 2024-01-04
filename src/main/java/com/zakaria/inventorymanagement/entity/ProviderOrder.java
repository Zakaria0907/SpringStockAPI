package com.zakaria.inventorymanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "providerorder")
public class ProviderOrder extends AbstractEntity{
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "orderDate")
	private Instant orderDate;
	
	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;
	
	@OneToMany(mappedBy = "providerOrder")
	private List<ProviderLineOrder> providerLineOrders;
}
