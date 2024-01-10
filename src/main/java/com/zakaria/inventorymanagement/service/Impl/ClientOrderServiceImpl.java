package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.ClientOrderDto;
import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.entity.ClientLineOrder;
import com.zakaria.inventorymanagement.entity.ClientOrder;
import com.zakaria.inventorymanagement.entity.Client;
import com.zakaria.inventorymanagement.entity.Item;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.mapper.impl.ClientLineOrderMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.ClientOrderMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.ClientMapperImpl;
import com.zakaria.inventorymanagement.repository.ClientLineOrderRepository;
import com.zakaria.inventorymanagement.repository.ClientOrderRepository;
import com.zakaria.inventorymanagement.repository.ClientRepository;
import com.zakaria.inventorymanagement.repository.ItemRepository;
import com.zakaria.inventorymanagement.service.ClientOrderService;
import com.zakaria.inventorymanagement.validator.ClientOrderValidator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ClientOrderServiceImpl implements ClientOrderService {
	
	private static final Logger log = LoggerFactory.getLogger(ClientOrderServiceImpl.class);
	
	private ClientOrderRepository clientOrderRepository;
	private ClientRepository clientRepository;
	private ClientLineOrderRepository clientLineOrderRepository;
	private ItemRepository itemRepository;
	
	private ClientOrderMapperImpl clientOrderMapper;
	private ClientMapperImpl clientMapper;
	private ClientLineOrderMapperImpl clientLineOrderMapper;
	
	@Autowired
	public ClientOrderServiceImpl(ClientOrderRepository clientOrderRepository,
	                              ClientRepository clientRepository,
	                              ClientOrderMapperImpl clientOrderMapper,
	                              ClientMapperImpl clientMapper,
	                              ClientLineOrderMapperImpl clientLineOrderMapper,
	                              ClientLineOrderRepository clientLineOrderRepository,
	                              ItemRepository itemRepository) {
		this.clientOrderRepository = clientOrderRepository;
		this.clientRepository = clientRepository;
		this.clientOrderMapper = clientOrderMapper;
		this.clientMapper = clientMapper;
		this.clientLineOrderMapper = clientLineOrderMapper;
		this.clientLineOrderRepository = clientLineOrderRepository;
		this.itemRepository = itemRepository;
	}
	
	@Override
	public ClientOrderDto save(ClientOrderDto clientOrderDto) {
		List<String> errors = ClientOrderValidator.validateClientOrder(clientOrderDto);
		if (!errors.isEmpty()) {
			log.error("ClientOrder is not valid {}", clientOrderDto);
			throw new InvalidEntityException("The client order is not valid", ErrorCodes.CLIENT_ORDER_NOT_VALID, errors);
		}
		
		if (clientOrderDto.getId() != null && clientOrderDto.getOrderStatus() == OrderStatus.DELIVERED) {
			throw new InvalidOperationException("Cannot modify the order once it is delivered", ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		
		if (clientOrderDto.getClient() != null) {
			if (clientOrderDto.getClient().getId() == null) {
				log.error("ClientOrder client ID is null");
				throw new InvalidOperationException("Cannot save an order with a null client ID", ErrorCodes.CLIENT_NOT_FOUND);
			}
			
			Client client = clientRepository.findById(clientOrderDto.getClient().getId())
					.orElseThrow(() -> new EntityNotFoundException(
							"No Client with ID " + clientOrderDto.getClient().getId() + " was found in the database",
							ErrorCodes.CLIENT_NOT_FOUND
					));
			
			clientOrderDto.setClient(clientMapper.mapTo(client));
		}
		
		ClientOrder clientOrder = clientOrderMapper.mapFrom(clientOrderDto);
		clientOrder.setOrderDate(Instant.now());
		clientOrder = clientOrderRepository.save(clientOrder);
		return clientOrderMapper.mapTo(clientOrder);
	}
	
	@Override
	public ClientOrderDto findById(Integer id) {
		if (id == null) {
			log.error("ClientOrder ID is null");
			throw new InvalidOperationException("ClientOrder ID cannot be null", ErrorCodes.CLIENT_ORDER_NOT_FOUND);
		}
		
		ClientOrder clientOrder = clientOrderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientOrder with ID = " + id + " found in the database",
						ErrorCodes.CLIENT_ORDER_NOT_FOUND
				));
		
		return clientOrderMapper.mapTo(clientOrder);
	}
	
	@Override
	public ClientOrderDto findByCode(String code) {
		if (!StringUtils.hasLength(code)) {
			log.error("ClientOrder code is null or empty");
			throw new InvalidOperationException("ClientOrder code cannot be null or empty", ErrorCodes.CLIENT_ORDER_NOT_FOUND);
		}
		
		ClientOrder clientOrder = clientOrderRepository.findClientOrderByCode(code)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientOrder with code = " + code + " found in the database",
						ErrorCodes.CLIENT_ORDER_NOT_FOUND
				));
		
		return clientOrderMapper.mapTo(clientOrder);
	}
	
	@Override
	public List<ClientOrderDto> findAll() {
		return clientOrderRepository.findAll()
				.stream()
				.map(clientOrderMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ClientLineOrderDto> findAllClientLineOrderByClientOrderId(Integer idOrder) {
		if (idOrder == null) {
			log.error("ClientOrder ID is null");
			throw new InvalidOperationException("ClientOrder ID cannot be null", ErrorCodes.CLIENT_LINE_ORDER_NOT_FOUND);
		}
		
		return clientLineOrderRepository.findAllByClientOrderId(idOrder)
				.stream()
				.map(clientLineOrderMapper::mapTo) // Assuming you have a clientLineOrderMapper
				.collect(Collectors.toList());
	}
	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("ClientOrder ID is null");
			throw new InvalidOperationException("Cannot delete a ClientOrder with null ID", ErrorCodes.CLIENT_ORDER_NOT_FOUND);
		}
		
		ClientOrder clientOrder = clientOrderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientOrder with ID = " + id + " found in the database",
						ErrorCodes.CLIENT_ORDER_NOT_FOUND
				));
		
		// This assumes that you cannot delete an order if it has line orders.
		// You should implement any additional business rules here.
		if (!clientOrder.getClientLineOrders().isEmpty()) {
			throw new InvalidOperationException("Cannot delete a ClientOrder that has ClientLineOrders", ErrorCodes.CLIENT_ORDER_ALREADY_IN_USE);
		}
		
		clientOrderRepository.deleteById(id);
	}
	
	@Override
	public ClientOrderDto updateOrderStatus(Integer idOrder, OrderStatus orderStatus) {
		ClientOrder clientOrder = clientOrderRepository.findById(idOrder)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientOrder with ID = " + idOrder + " found in the database",
						ErrorCodes.CLIENT_ORDER_NOT_FOUND
				));
		
		if (clientOrder.getOrderStatus().equals(OrderStatus.DELIVERED)) {
			throw new InvalidOperationException("Cannot change the status of a delivered order",
					ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		
		clientOrder.setOrderStatus(orderStatus);
		clientOrderRepository.save(clientOrder);
		return clientOrderMapper.mapTo(clientOrder);
	}
	
	public ClientOrderDto updateQuantity(Integer idOrder, Integer idLineOrder, BigDecimal quantity) {
		if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidOperationException("Quantity must be greater than zero", ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		
		// Assuming there is a method to find a line order by its id
		ClientLineOrder lineOrder = clientLineOrderRepository.findById(idLineOrder)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientLineOrder with ID = " + idLineOrder + " found in the database",
						ErrorCodes.CLIENT_LINE_ORDER_NOT_FOUND
				));
		
		lineOrder.setQuantity(quantity);
		clientLineOrderRepository.save(lineOrder);
		
		// Assuming a method to get ClientOrderDto from a ClientOrder entity
		return clientOrderMapper.mapTo(lineOrder.getClientOrder());
	}
	
	public ClientOrderDto updateClient(Integer idOrder, Integer idClient) {
		Client client = clientRepository.findById(idClient)
				.orElseThrow(() -> new EntityNotFoundException(
						"No Client with ID = " + idClient + " found in the database",
						ErrorCodes.CLIENT_NOT_FOUND
				));
		
		ClientOrder clientOrder = clientOrderRepository.findById(idOrder)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientOrder with ID = " + idOrder + " found in the database",
						ErrorCodes.CLIENT_ORDER_NOT_FOUND
				));
		
		clientOrder.setClient(client);
		clientOrderRepository.save(clientOrder);
		return clientOrderMapper.mapTo(clientOrder);
	}
	
	public ClientOrderDto deleteItem(Integer idOrder, Integer idLineOrder) {
		ClientOrder clientOrder = clientOrderRepository.findById(idOrder)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientOrder with ID = " + idOrder + " found in the database",
						ErrorCodes.CLIENT_ORDER_NOT_FOUND
				));
		
		ClientLineOrder lineOrder = clientLineOrderRepository.findById(idLineOrder)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientLineOrder with ID = " + idLineOrder + " found in the database",
						ErrorCodes.CLIENT_LINE_ORDER_NOT_FOUND
				));
		
		if (!lineOrder.getClientOrder().getId().equals(clientOrder.getId())) {
			throw new InvalidOperationException("Line order does not belong to the order", ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		
		clientLineOrderRepository.delete(lineOrder);
		return clientOrderMapper.mapTo(clientOrder);
	}
	
	@Override
	public ClientOrderDto updateItem(Integer idOrder, Integer idLineOrder, Integer newItemId) {
		// Validate the IDs
		if (idOrder == null || idLineOrder == null || newItemId == null) {
			throw new InvalidOperationException("Order ID, Line Order ID, and Item ID cannot be null", ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		
		// Fetch the line order
		ClientLineOrder lineOrder = clientLineOrderRepository.findById(idLineOrder)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientLineOrder with ID = " + idLineOrder + " found in the database",
						ErrorCodes.CLIENT_LINE_ORDER_NOT_FOUND
				));
		
		// Fetch the order
		ClientOrder clientOrder = clientOrderRepository.findById(idOrder)
				.orElseThrow(() -> new EntityNotFoundException(
						"No ClientOrder with ID = " + idOrder + " found in the database",
						ErrorCodes.CLIENT_ORDER_NOT_FOUND
				));
		
		// Check if the order status allows modification
		if (clientOrder.getOrderStatus().equals(OrderStatus.DELIVERED)) {
			throw new InvalidOperationException("Cannot change the item of a delivered order",
					ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		
		// Fetch the new item
		Item newItem = itemRepository.findById(newItemId)
				.orElseThrow(() -> new EntityNotFoundException(
						"No Item with ID = " + newItemId + " found in the database",
						ErrorCodes.ITEM_NOT_FOUND
				));
		
		// Update the line order
		lineOrder.setItem(newItem);
		clientLineOrderRepository.save(lineOrder);
		
		// Return the updated order
		return clientOrderMapper.mapTo(clientOrder);
	}
}
