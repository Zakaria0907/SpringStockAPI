package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.AUTHENTICATION_ENDPOINT;

import com.zakaria.inventorymanagement.dto.auth.AuthenticationRequest;
import com.zakaria.inventorymanagement.dto.auth.AuthenticationResponse;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("authentication")
public interface AuthenticationApi {
	
	@PostMapping(AUTHENTICATION_ENDPOINT + "/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);
	
}
