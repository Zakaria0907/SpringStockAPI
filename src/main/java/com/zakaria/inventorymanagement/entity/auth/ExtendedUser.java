package com.zakaria.inventorymanagement.entity.auth;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


@Getter
@Setter
public class ExtendedUser extends User {
	
	private Integer companyId;
	
	public ExtendedUser(String username, String password,
	                    Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public ExtendedUser(String username, String password, Integer companyId,
	                    Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.companyId = companyId;
	}
}
