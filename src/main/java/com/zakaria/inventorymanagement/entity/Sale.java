package com.zakaria.inventorymanagement.entity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "sale")
public class Sale extends AbstractEntity {
	
	@Column(name = "company_id")
	private Integer companyId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "saledate")
	private Instant saleDate;
	
	@Column(name= "comment")
	private String comment;
	
	@OneToMany(mappedBy = "sale")
	private List<SaleLineItem> saleLineItems;
}
