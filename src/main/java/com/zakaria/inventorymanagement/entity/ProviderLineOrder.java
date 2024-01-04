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
@Table(name= "providerlineorder")
public class ProviderLineOrder extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "providerorder_id")
	private ProviderOrder providerOrder;
}
