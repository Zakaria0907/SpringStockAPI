package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "clientlineorder")
public class ClientLineOrder extends AbstractEntity {
	
	@Column(name = "itemname")
	private Integer companyId;
	
	@Column(name = "quantity")
	private BigDecimal quantity;
	
	@Column(name = "unitprice")
	private BigDecimal unitPrice;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "clientorder_id")
	private ClientOrder clientOrder;
	
	
}
