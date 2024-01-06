package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "stockmovement")
public class InventoryMovement extends AbstractEntity {
	
	@Column(name = "itemname")
	private Integer companyId;
	@Column(name = "movementdate")
	private Instant MovementDate;
	
	@Column(name = "quantity")
	private BigDecimal quantity;
	
	@Column(name = "movementtype")
	private MovementType movementType;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
}
