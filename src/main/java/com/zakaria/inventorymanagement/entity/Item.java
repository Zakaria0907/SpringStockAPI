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
@Table(name = "item")
public class Item extends AbstractEntity{
	
	@Column(name = "itemcode")
	private String itemCode;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "unitpricebt")
	private BigDecimal unitPriceBT;
	
	@Column(name = "tax")
	private BigDecimal tax;
	
	@Column(name = "unitpriceat")
	private BigDecimal unitPriceAT;
	
	@Column(name = "photo")
	private String photo;
	
	@ManyToOne
	@JoinColumn(name = "idcategory")
	private Category category;
}
