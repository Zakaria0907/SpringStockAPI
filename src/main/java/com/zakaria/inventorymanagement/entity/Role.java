package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "role")
public class Role extends AbstractEntity {
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "companyuser_id")
	private CompanyUser companyUser;
}
