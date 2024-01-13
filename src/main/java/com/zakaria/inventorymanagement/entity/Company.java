package com.zakaria.inventorymanagement.entity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "company")
public class Company extends AbstractEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Embedded
	private Address address;

	@Column(name = "fiscalcode")
	private String fiscalCode;

	@Column(name = "photo")
	private String photo;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phoneNumber;

	@Column(name = "website")
	private String website;

	@OneToMany(mappedBy = "company")
	private List<CompanyUser> companyUsers;
}
