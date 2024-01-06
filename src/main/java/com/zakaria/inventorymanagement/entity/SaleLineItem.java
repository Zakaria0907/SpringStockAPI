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
@Table(name= "salelineitem")
public class SaleLineItem extends AbstractEntity {
	
	@Column(name = "itemname")
	private Integer companyId;
	
	@ManyToOne
	@JoinColumn(name = "sale_id")
	private Sale sale;
	
	@Column(name = "quantity")
	private BigDecimal quantity;
	
	@Column(name = "unitprice")
	private BigDecimal unitPrice;
}
