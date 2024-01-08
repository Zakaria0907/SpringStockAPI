package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.ClientOrderDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientOrderValidator {
	
	public static List<String> validateClientOrder(ClientOrderDto clientOrderDto) {
		List<String> errors = new ArrayList<>();
		
		if (clientOrderDto == null) {
			errors.add("Please provide the order code");
			errors.add("Please provide the order date");
			errors.add("Please provide the client details");
			
			return errors;
		}
		
		if (!StringUtils.hasLength(clientOrderDto.getCode())) {
			errors.add("Please provide the order code");
		}
		if (clientOrderDto.getOrderDate() == null) {
			errors.add("Please provide the order date");
		}
		// TODO: fix me
		if (!StringUtils.hasLength(clientOrderDto.getOrderStatus().toString())) {
			errors.add("Veuillez renseigner l'etat de la commande");
		}
		if (clientOrderDto.getClient() == null || clientOrderDto.getClient().getId() == null) {
			errors.add("Veuillez renseigner le client");
		}
		
		return errors;
	}
}
