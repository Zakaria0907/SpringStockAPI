package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.controller.api.AuthenticationApi;
import com.zakaria.inventorymanagement.dto.auth.AuthenticationRequest;
import com.zakaria.inventorymanagement.dto.auth.AuthenticationResponse;
import com.zakaria.inventorymanagement.entity.auth.ExtendedUser; // Changed from com.bouali.gestiondestock.model.auth.ExtendedUser
import com.zakaria.inventorymanagement.service.auth.ApplicationUserDetailService; // Changed from com.bouali.gestiondestock.services.auth.ApplicationUserDetailsService
import com.zakaria.inventorymanagement.utils.JWTutils; // Changed from com.bouali.gestiondestock.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements AuthenticationApi{
	
	
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ApplicationUserDetailService userDetailsService;
	
	@Autowired
	private JWTutils jwtUtils;
	
	@Override
	public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getLogin(),
						request.getPassword()
				)
		);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
		
		final String jwt = jwtUtils.generateToken((ExtendedUser) userDetails); // Changed from JwtUtil
		
		return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
	}
	
}
