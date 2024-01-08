package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "providerlineorder")
public class ProviderLineOrder extends AbstractEntity {
	
	@Column(name = "company_id")
	private Integer companyId;
	
	@Column(name = "quantity")
	private BigDecimal quantity;
	
	@Column(name = "unitprice")
	private BigDecimal unitPrice;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "providerorder_id")
	private ProviderOrder providerOrder;
}
