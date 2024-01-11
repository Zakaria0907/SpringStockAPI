package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.ClientOrderDto;
import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import com.zakaria.inventorymanagement.entity.*;
import com.zakaria.inventorymanagement.entity.enums.MovementSource;
import com.zakaria.inventorymanagement.entity.enums.MovementType;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.mapper.impl.ClientLineOrderMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.ClientOrderMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.ClientMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.ItemMapperImpl;
import com.zakaria.inventorymanagement.repository.ClientLineOrderRepository;
import com.zakaria.inventorymanagement.repository.ClientOrderRepository;
import com.zakaria.inventorymanagement.repository.ClientRepository;
import com.zakaria.inventorymanagement.repository.ItemRepository;
import com.zakaria.inventorymanagement.service.ClientOrderService;
import com.zakaria.inventorymanagement.validator.ClientOrderValidator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
	private ItemMapperImpl itemMapper;
	
	private InventoryMovementServiceImpl inventoryMovementService;
	
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
		}
			
			List<String> itemErrors = new ArrayList<>();
			
			if(clientOrderDto.getClientLineOrders() != null) {
				clientOrderDto.getClientLineOrders().forEach(clientLineOrderDto -> {
					if (clientLineOrderDto.getItem() != null) {
						Optional<Item> item = itemRepository.findById(clientLineOrderDto.getItem().getId());
						if (item.isEmpty()) {
							itemErrors.add("Item with ID " + clientLineOrderDto.getItem().getId() + " does not exist");
						}
					} else {
						itemErrors.add("Item cannot be null");
					}
				});
			}
			
			if (!itemErrors.isEmpty()) {
				log.warn(" ");
				throw new InvalidEntityException("Invalid item", ErrorCodes.ITEM_NOT_VALID, itemErrors);
			}
			
			clientOrderDto.setOrderDate(Instant.now());
			ClientOrder savedClientOrder = clientOrderRepository.save(clientOrderMapper.mapFrom(clientOrderDto));
			
			if(clientOrderDto.getClientLineOrders() != null) {
				clientOrderDto.getClientLineOrders().forEach(clientLineOrderDto -> {
					ClientLineOrder clientLineOrder = clientLineOrderMapper.mapFrom(clientLineOrderDto);
					clientLineOrder.setClientOrder(savedClientOrder);
					clientLineOrder.setCompanyId(clientLineOrderDto.getCompanyId());
					ClientLineOrder savedClientLineOrder = clientLineOrderRepository.save(clientLineOrder);
					
					performExit(savedClientLineOrder);
				});
		}
		
		return clientOrderMapper.mapTo(savedClientOrder);
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
	public ClientOrderDto updateOrderStatus(Integer orderId, OrderStatus status) {
		checkOrderId(orderId);
		if (status == null) {
			log.error("Order status is NULL");
			throw new InvalidOperationException("Cannot modify the order with a null status",
					ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		ClientOrderDto clientOrder = checkOrderStatus(orderId);
		clientOrder.setOrderStatus(status);
		
		ClientOrder savedClientOrder = clientOrderRepository.save(clientOrderMapper.mapFrom(clientOrder));
		if (clientOrder.isOrderDelivered()) {
			updateInventoryMovement(orderId);
		}
		
		return clientOrderMapper.mapTo(savedClientOrder);
	}
	
	@Override
	public ClientOrderDto updateQuantity(Integer orderId, Integer lineOrderId, BigDecimal quantity) {
		checkOrderId(orderId);
		checkLineOrderId(lineOrderId);
		
		if (quantity == null || quantity.compareTo(BigDecimal.ZERO) == 0) {
			log.error("The line order ID is NULL");
			throw new InvalidOperationException("Cannot modify the order with a null or ZERO quantity",
					ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		
		ClientOrderDto clientOrder = checkOrderStatus(orderId);
		ClientLineOrder lineOrder = findClientLineOrder(lineOrderId).orElseThrow(() ->
				new EntityNotFoundException("No client line order found with ID " + lineOrderId,
						ErrorCodes.CLIENT_LINE_ORDER_NOT_FOUND));
		
		lineOrder.setQuantity(quantity);
		clientLineOrderRepository.save(lineOrder);
		
		return clientOrder;
	}
	
	@Override
	public ClientOrderDto updateClient(Integer orderId, Integer clientId) {
		checkOrderId(orderId);
		if (clientId == null) {
			log.error("Client ID is NULL");
			throw new InvalidOperationException("Cannot modify the order with a null client ID",
					ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		ClientOrderDto clientOrder = checkOrderStatus(orderId);
		Client client = clientRepository.findById(clientId).orElseThrow(() ->
				new EntityNotFoundException("No client found with ID " + clientId, ErrorCodes.CLIENT_NOT_FOUND));
		
		clientOrder.setClient(clientMapper.mapTo(client));
		
		return clientOrderMapper.mapTo(clientOrderRepository.save(clientOrderMapper.mapFrom(clientOrder)));
	}
	
	@Override
	public ClientOrderDto updateItem(Integer orderId, Integer lineOrderId, Integer itemId) {
		checkOrderId(orderId);
		checkLineOrderId(lineOrderId);
		checkItemId(itemId, "new");
		
		ClientOrderDto clientOrder = checkOrderStatus(orderId);
		ClientLineOrder lineOrder = findClientLineOrder(lineOrderId).orElseThrow(() ->
				new EntityNotFoundException("No client line order found with ID " + lineOrderId,
						ErrorCodes.CLIENT_LINE_ORDER_NOT_FOUND));
		
		Item item = itemRepository.findById(itemId).orElseThrow(() ->
				new EntityNotFoundException("No item found with ID " + itemId, ErrorCodes.ITEM_NOT_FOUND));
		
		lineOrder.setItem(item);
		clientLineOrderRepository.save(lineOrder);
		
		return clientOrder;
	}
	
	@Override
	public ClientOrderDto deleteItem(Integer orderId, Integer lineOrderId) {
		checkOrderId(orderId);
		checkLineOrderId(lineOrderId);
		
		ClientOrderDto clientOrder = checkOrderStatus(orderId);
		findClientLineOrder(lineOrderId).orElseThrow(() ->
				new EntityNotFoundException("No client line order found with ID " + lineOrderId,
						ErrorCodes.CLIENT_LINE_ORDER_NOT_FOUND));
		
		clientLineOrderRepository.deleteById(lineOrderId);
		
		return clientOrder;
	}
	
	// Helper function
	
	private ClientOrderDto checkOrderStatus(Integer orderId) {
		ClientOrderDto clientOrder = findById(orderId);
		if (clientOrder.isOrderDelivered()) {
			throw new InvalidOperationException("Cannot modify the order once it is delivered",
					ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
		return clientOrder;
	}
	
	private Optional<ClientLineOrder> findClientLineOrder(Integer lineOrderId) {
		Optional<ClientLineOrder> lineOrderOptional = clientLineOrderRepository.findById(lineOrderId);
		if (lineOrderOptional.isEmpty()) {
			throw new EntityNotFoundException("No client line order found with ID " + lineOrderId,
					ErrorCodes.CLIENT_LINE_ORDER_NOT_FOUND);
		}
		return lineOrderOptional;
	}
	
	private void checkOrderId(Integer orderId) {
		if (orderId == null) {
			log.error("Client order ID is NULL");
			throw new InvalidOperationException("Cannot modify the order with a null ID",
					ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
	}
	
	private void checkLineOrderId(Integer lineOrderId) {
		if (lineOrderId == null) {
			log.error("The line order ID is NULL");
			throw new InvalidOperationException("Cannot modify the order with a null line order ID",
					ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
	}
	
	private void checkItemId(Integer itemId, String newItem) {
		if (itemId == null) {
			log.error("The ID of " + newItem + " is NULL");
			throw new InvalidOperationException("Cannot modify the order with a null " + newItem + " ID item",
					ErrorCodes.CLIENT_ORDER_NOT_MODIFIABLE);
		}
	}
	
	private void updateInventoryMovement(Integer orderId) {
		List<ClientLineOrder> lineOrders = clientLineOrderRepository.findAllByClientOrderId(orderId);
		lineOrders.forEach(this::performExit);
	}
	
	private void performExit(ClientLineOrder clientLineOrder) {
		InventoryMovementDto invMovDto = InventoryMovementDto.builder()
				.item(itemMapper.mapTo(clientLineOrder.getItem()))
				.quantity(clientLineOrder.getQuantity())
				.movementDate(Instant.now())
				.movementType(MovementType.EXIT)
				.movementSource(MovementSource.CLIENT_ORDER)
				.companyId(clientLineOrder.getCompanyId())
				.build();
		inventoryMovementService.exitInventory(invMovDto);
	}
}

