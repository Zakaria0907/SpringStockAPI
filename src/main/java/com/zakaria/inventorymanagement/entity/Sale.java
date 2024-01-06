package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "sale")
public class Sale extends AbstractEntity {
	
	@Column(name = "itemname")
	private Integer companyId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "saledate")
	private Instant saleDate;
	
	@Column(name= "comment")
	private String comment;
}
