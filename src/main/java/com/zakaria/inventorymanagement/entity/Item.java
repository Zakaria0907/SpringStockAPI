package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "item")
public class Item extends AbstractEntity{
	
	@Column(name = "itemname")
	private Integer companyId;
	
	@Column(name = "itemcode")
	private String itemCode;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "unitpricebeforetax")
	private BigDecimal unitPriceBeforeTax;
	
	@Column(name = "tax")
	private BigDecimal tax;
	
	@Column(name = "unitpriceaftertax")
	private BigDecimal unitPriceAfterTax;
	
	@Column(name = "photo")
	private String photo;
	
	@ManyToOne
	@JoinColumn(name = "idcategory")
	private Category category;
}
