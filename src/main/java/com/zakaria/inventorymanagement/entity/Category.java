package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "category")
public class Category extends AbstractEntity {
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(mappedBy = "category")
	private List<Item> items;
	
	
}
