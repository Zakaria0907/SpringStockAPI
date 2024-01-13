package com.zakaria.inventorymanagement.entity;

import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "providerorder")
public class ProviderOrder extends AbstractEntity{
	
	@Column(name = "company_id")
	private Integer companyId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "orderDate")
	private Instant orderDate;
	
	@Column(name = "orderstatus")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@ManyToOne
	@JoinColumn(name = "provider_id")
	private Provider provider;
	
	@OneToMany(mappedBy = "providerOrder")
	private List<ProviderLineOrder> providerLineOrders;
}
