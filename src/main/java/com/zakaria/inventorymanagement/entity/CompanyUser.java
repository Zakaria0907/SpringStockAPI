package com.zakaria.inventorymanagement.entity;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "companyuser")
public class CompanyUser extends AbstractEntity{
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "dateofbirth")
	private Instant dateOfBirth;
	
	@Column(name = "password")
	private String password;
	
	@Embedded
	private Address address;
	
	@Column(name = "photo")
	private String photo;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	@OneToMany(fetch= FetchType.EAGER, mappedBy = "companyUser")
	private List<Role> roles;
}
