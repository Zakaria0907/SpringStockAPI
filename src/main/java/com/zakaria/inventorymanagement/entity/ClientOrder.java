package com.zakaria.inventorymanagement.entity;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name= "clientorder")
public class ClientOrder extends AbstractEntity {
	
	@Column(name = "company_id")
	private Integer companyId;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "orderdate")
	private Instant OrderDate;
	
	@Column(name = "orderstatus")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@OneToMany(mappedBy = "clientOrder")
	private List<ClientLineOrder> clientLineOrders;
}
