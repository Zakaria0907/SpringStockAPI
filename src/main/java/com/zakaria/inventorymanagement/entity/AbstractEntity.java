package com.zakaria.inventorymanagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {
	
	@Id
	@GeneratedValue(generator = "increment")
	private Integer id;
	
	@CreatedDate
	@Column(name="creationDate", nullable = false, updatable = false)
	@JsonIgnore
	private Instant creationDate;
	
	
	@LastModifiedDate
	@Column(name="lastModifiedDate", nullable = false)
	@JsonIgnore
	private Instant lastModifiedDate;
}
