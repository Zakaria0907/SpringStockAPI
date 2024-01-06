package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "client")
public class Client extends AbstractEntity {
	
	@Column(name = "itemname")
	private Integer companyId;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Embedded
	private Address address;
	
	@Column(name = "photo")
	private String photo;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phoneNumber;
	
	@OneToMany(mappedBy = "client")
	private List<ClientOrder> clientOrders;
}
