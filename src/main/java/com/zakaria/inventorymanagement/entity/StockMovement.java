package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "stockmovement")
public class StockMovement extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
}
