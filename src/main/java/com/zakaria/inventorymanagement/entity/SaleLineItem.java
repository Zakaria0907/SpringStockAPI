package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	
	@ManyToOne
	@JoinColumn(name = "sale_id")
	private Sale sale;
	
	private BigDecimal quantity;
}
