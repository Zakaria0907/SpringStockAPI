package com.zakaria.inventorymanagement.service.auth;

import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import com.zakaria.inventorymanagement.entity.auth.ExtendedUser;
import com.zakaria.inventorymanagement.service.CompanyUserService;
import java.util.ArrayList;
import java.util.List;

import com.zakaria.inventorymanagement.service.Impl.CompanyUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailService implements UserDetailsService {
	
	@Autowired
	private CompanyUserServiceImpl service;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		CompanyUserDto user = service.findByEmail(email);
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		
		return new ExtendedUser(user.getEmail(), user.getPassword(), user.getCompany().getId(), authorities);
	}
}
