package com.zakaria.inventorymanagement.entity;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "category")
public class Category extends AbstractEntity {
	
	@Column(name = "company_id")
	private Integer companyId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(mappedBy = "category")
	private List<Item> items;
	
	
}
