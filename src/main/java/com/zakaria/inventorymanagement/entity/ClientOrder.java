package com.zakaria.inventorymanagement.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "clientorder")
public class ClientOrder extends AbstractEntity {
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "date")
	private Instant date;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@OneToMany(mappedBy = "clientOrder")
	private List<ClientLineOrder> clientLineOrders;
}
