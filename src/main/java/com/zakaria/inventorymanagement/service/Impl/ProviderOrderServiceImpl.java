package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import com.zakaria.inventorymanagement.dto.ItemDto;
import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import com.zakaria.inventorymanagement.dto.ProviderOrderDto;
import com.zakaria.inventorymanagement.entity.*;
import com.zakaria.inventorymanagement.entity.enums.MovementSource;
import com.zakaria.inventorymanagement.entity.enums.MovementType;
import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.mapper.impl.ItemMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.ProviderOrderMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.ProviderLineOrderMapperImpl;
import com.zakaria.inventorymanagement.repository.ItemRepository;
import com.zakaria.inventorymanagement.repository.ProviderLineOrderRepository;
import com.zakaria.inventorymanagement.repository.ProviderOrderRepository;
import com.zakaria.inventorymanagement.repository.ProviderRepository;
import com.zakaria.inventorymanagement.service.InventoryMovementService;
import com.zakaria.inventorymanagement.service.ProviderOrderService;
import com.zakaria.inventorymanagement.validator.ProviderOrderValidator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class ProviderOrderServiceImpl implements ProviderOrderService {
	
	private ProviderOrderRepository providerOrderRepository;
	private ProviderLineOrderRepository providerLineOrderRepository;
	private ProviderRepository providerRepository;
	private ItemRepository itemRepository;
	private InventoryMovementService inventoryMovementService;
	
	private ProviderOrderMapperImpl providerOrderMapper;
	private ProviderLineOrderMapperImpl providerLineOrderMapper;
	private ItemMapperImpl itemMapper;
	
	
	// Additional variables here if needed
	
	@Autowired
	public ProviderOrderServiceImpl(ProviderOrderRepository providerOrderRepository,
	                                ProviderRepository providerRepository,
	                                ProviderLineOrderRepository providerLineOrderRepository,
	                                ItemRepository itemRepository,
	                                InventoryMovementService inventoryMovementService,
	                                ProviderOrderMapperImpl providerOrderMapper,
	                                ProviderLineOrderMapperImpl providerLineOrderMapper,
	                                ItemMapperImpl itemMapper) {
		this.providerOrderRepository = providerOrderRepository;
		this.providerRepository = providerRepository;
		this.providerLineOrderRepository = providerLineOrderRepository;
		this.itemRepository = itemRepository;
		this.inventoryMovementService = inventoryMovementService;
		this.providerOrderMapper = providerOrderMapper;
		this.providerLineOrderMapper = providerLineOrderMapper;
		this.itemMapper = itemMapper;
	}
	
	
	@Override
	public ProviderOrderDto save(ProviderOrderDto dto) {
		List<String> errors = ProviderOrderValidator.validateProviderOrder(dto);
		if (!errors.isEmpty()) {
			log.error("Provider order is not valid");
			throw new InvalidEntityException("The provider order is not valid", ErrorCodes.PROVIDER_ORDER_NOT_VALID, errors);
		}
		
		if (dto.getId() != null && dto.isOrderDelivered()) {
			throw new InvalidOperationException("Cannot modify the order once it is delivered", ErrorCodes.PROVIDER_ORDER_NOT_MODIFIABLE);
		}
		
		Optional<Provider> provider = providerRepository.findById(dto.getProvider().getId());
		if (provider.isEmpty()) {
			log.warn("Provider with ID {} was not found in the DB", dto.getProvider().getId());
			throw new EntityNotFoundException("No provider with ID " + dto.getProvider().getId() + " was found in the DB",
					ErrorCodes.PROVIDER_NOT_FOUND);
		}
		
		List<String> articleErrors = new ArrayList<>();
		if (dto.getProviderLineOrders() != null) {
			dto.getProviderLineOrders().forEach(lineOrder -> {
				if (lineOrder.getItem() != null) {
					Optional<Item> article = itemRepository.findById(lineOrder.getItem().getId());
					if (article.isEmpty()) {
						articleErrors.add("The article with ID " + lineOrder.getItem().getId() + " does not exist");
					}
				} else {
					articleErrors.add("Cannot register an order with a NULL article");
				}
			});
		}
		
		if (!articleErrors.isEmpty()) {
			log.warn("Article validation errors");
			throw new InvalidEntityException("Article does not exist in the DB", ErrorCodes.ITEM_NOT_FOUND, articleErrors);
		}
		
		dto.setOrderDate(Instant.now());
		ProviderOrder savedOrder = providerOrderRepository.save(providerOrderMapper.mapFrom(dto));
		
		if (dto.getProviderLineOrders() != null) {
			dto.getProviderLineOrders().forEach(lineOrderDto -> {
				ProviderLineOrder lineOrder = providerLineOrderMapper.mapFrom(lineOrderDto);
				lineOrder.setProviderOrder(savedOrder);
				lineOrder.setCompanyId(savedOrder.getCompanyId());
				ProviderLineOrder savedLineOrder = providerLineOrderRepository.save(lineOrder);
				
				performStockEntry(savedLineOrder);
			});
		}
		
		return providerOrderMapper.mapTo(savedOrder);
	}
	
	@Override
	public ProviderOrderDto updateOrderStatus(Integer providerOrderId, OrderStatus newStatus) {
		checkProviderOrderId(providerOrderId);
		
		if (newStatus == null) {
			log.error("The status of the provider order is NULL");
			throw new InvalidOperationException("Cannot modify the status of the order with a null status",
					ErrorCodes.PROVIDER_ORDER_NOT_MODIFIABLE);
		}
		
		ProviderOrderDto providerOrderDto = findById(providerOrderId);
		if (providerOrderDto == null) {
			throw new EntityNotFoundException(
					"No provider order was found with ID " + providerOrderId, ErrorCodes.PROVIDER_ORDER_NOT_FOUND);
		}
		
		if (providerOrderDto.isOrderDelivered()) {
			throw new InvalidOperationException("Cannot modify the order once it is delivered",
					ErrorCodes.PROVIDER_ORDER_NOT_MODIFIABLE);
		}
		
		providerOrderDto.setOrderStatus(newStatus);
		ProviderOrder updatedOrder = providerOrderRepository.save(providerOrderMapper.mapFrom(providerOrderDto));
		
		if (providerOrderDto.isOrderDelivered()) {
			updateStockMovement(providerOrderId);
		}
		
		return providerOrderMapper.mapTo(updatedOrder);
	}
	
	
	@Override
	public ProviderOrderDto updateQuantity(Integer idOrder, Integer idLineOrder, BigDecimal quantity) {
		checkProviderOrderId(idOrder);
		checkProviderLineOrderId(idLineOrder);
		
		if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
			log.error("Quantity is NULL or less than or equal to ZERO");
			throw new InvalidOperationException("Cannot update the order with a null or non-positive quantity",
					ErrorCodes.PROVIDER_ORDER_NOT_MODIFIABLE);
		}
		
		ProviderOrder providerOrder = providerOrderRepository.findById(idOrder)
				.orElseThrow(() -> new EntityNotFoundException("No provider order found with ID " + idOrder,
						ErrorCodes.PROVIDER_ORDER_NOT_FOUND));
		
		ProviderLineOrder lineOrder = providerLineOrderRepository.findById(idLineOrder)
				.orElseThrow(() -> new EntityNotFoundException("No provider line order found with ID " + idLineOrder,
						ErrorCodes.PROVIDER_LINE_ORDER_NOT_FOUND));
		
		lineOrder.setQuantity(quantity);
		providerLineOrderRepository.save(lineOrder);
		
		return providerOrderMapper.mapTo(providerOrder);
	}
	
	
	@Override
	public ProviderOrderDto updateProvider(Integer idOrder, Integer idProvider) {
		checkProviderOrderId(idOrder);
		
		ProviderOrder providerOrder = providerOrderRepository.findById(idOrder)
				.orElseThrow(() -> new EntityNotFoundException("No provider order found with ID " + idOrder,
						ErrorCodes.PROVIDER_ORDER_NOT_FOUND));
		
		Provider provider = providerRepository.findById(idProvider)
				.orElseThrow(() -> new EntityNotFoundException("No provider found with ID " + idProvider,
						ErrorCodes.PROVIDER_NOT_FOUND));
		
		providerOrder.setProvider(provider);
		providerOrderRepository.save(providerOrder);
		
		return providerOrderMapper.mapTo(providerOrder);
	}
	
	
	@Override
	public ProviderOrderDto updateItem(Integer idOrder, Integer idLineOrder, Integer idItem) {
		checkProviderOrderId(idOrder);
		checkProviderLineOrderId(idLineOrder);
		
		ProviderOrder providerOrder = providerOrderRepository.findById(idOrder)
				.orElseThrow(() -> new EntityNotFoundException("No provider order found with ID " + idOrder,
						ErrorCodes.PROVIDER_ORDER_NOT_FOUND));
		
		ProviderLineOrder lineOrder = providerLineOrderRepository.findById(idLineOrder)
				.orElseThrow(() -> new EntityNotFoundException("No provider line order found with ID " + idLineOrder,
						ErrorCodes.PROVIDER_LINE_ORDER_NOT_FOUND));
		
		Item item = itemRepository.findById(idItem)
				.orElseThrow(() -> new EntityNotFoundException("No item found with ID " + idItem,
						ErrorCodes.ITEM_NOT_FOUND));
		
		lineOrder.setItem(item);
		providerLineOrderRepository.save(lineOrder);
		
		return providerOrderMapper.mapTo(providerOrder);
	}
	
	
	@Override
	public ProviderOrderDto deleteItem(Integer idOrder, Integer idLineOrder) {
		checkProviderOrderId(idOrder);
		checkProviderLineOrderId(idLineOrder);
		
		ProviderLineOrder lineOrder = providerLineOrderRepository.findById(idLineOrder)
				.orElseThrow(() -> new EntityNotFoundException("No provider line order found with ID " + idLineOrder,
						ErrorCodes.PROVIDER_LINE_ORDER_NOT_FOUND));
		
		providerLineOrderRepository.deleteById(idLineOrder);
		
		ProviderOrder providerOrder = providerOrderRepository.findById(idOrder)
				.orElseThrow(() -> new EntityNotFoundException("No provider order found with ID " + idOrder,
						ErrorCodes.PROVIDER_ORDER_NOT_FOUND));
		
		return providerOrderMapper.mapTo(providerOrder);
	}
	
	
	@Override
	public ProviderOrderDto findById(Integer id) {
		if (id == null) {
			log.error("Provider order ID is NULL");
			throw new InvalidOperationException("Provider order ID cannot be null", ErrorCodes.PROVIDER_ORDER_NOT_VALID);
		}
		
		return providerOrderRepository.findById(id)
				.map(providerOrderMapper::mapTo)
				.orElseThrow(() -> new EntityNotFoundException(
						"No provider order was found with ID " + id, ErrorCodes.PROVIDER_ORDER_NOT_FOUND
				));
	}
	
	
	@Override
	public ProviderOrderDto findByCode(String code) {
		if (!StringUtils.hasLength(code)) {
			log.error("Provider order CODE is NULL or empty");
			throw new InvalidOperationException("Provider order code cannot be null or empty", ErrorCodes.PROVIDER_ORDER_NOT_VALID);
		}
		
		return providerOrderRepository.findProviderOrderByCode(code)
				.map(providerOrderMapper::mapTo)
				.orElseThrow(() -> new EntityNotFoundException(
						"No provider order was found with the CODE " + code, ErrorCodes.PROVIDER_ORDER_NOT_FOUND
				));
	}
	
	
	@Override
	public List<ProviderOrderDto> findAll() {
		return providerOrderRepository.findAll().stream()
				.map(providerOrderMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ProviderLineOrderDto> findAllProviderLineOrdersByProviderOrderId(Integer providerOrderId) {
		return providerLineOrderRepository.findAllByProviderOrderId(providerOrderId).stream()
				.map(providerLineOrderMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public void delete(Integer idOrder) {
		checkProviderOrderId(idOrder);
		
		List<ProviderLineOrder> lineOrders = providerLineOrderRepository.findAllByProviderOrderId(idOrder);
		if (!lineOrders.isEmpty()) {
			throw new InvalidOperationException("Cannot delete a provider order that is already in use",
					ErrorCodes.PROVIDER_ORDER_ALREADY_IN_USE);
		}
		
		providerOrderRepository.deleteById(idOrder);
	}
	
	
	private void checkProviderOrderId(Integer providerOrderId) {
		if (providerOrderId == null) {
			log.error("Provider order ID is NULL");
			throw new InvalidOperationException("Cannot modify the order with a null ID",
					ErrorCodes.PROVIDER_ORDER_NOT_MODIFIABLE);
		}
	}
	
	private void checkProviderLineOrderId(Integer providerLineOrderId) {
		if (providerLineOrderId == null) {
			log.error("Provider line order ID is NULL");
			throw new InvalidOperationException("Cannot modify the order state with a null line order ID",
					ErrorCodes.PROVIDER_ORDER_NOT_MODIFIABLE);
		}
	}
	
	private void checkArticleId(Integer articleId, String message) {
		if (articleId == null) {
			log.error("Article ID for " + message + " is NULL");
			throw new InvalidOperationException("Cannot modify the order state with a null " + message + " article ID",
					ErrorCodes.PROVIDER_ORDER_NOT_MODIFIABLE);
		}
	}
	
	private void updateStockMovement(Integer providerOrderId) {
		List<ProviderLineOrder> providerLineOrders = providerLineOrderRepository.findAllByProviderOrderId(providerOrderId);
		providerLineOrders.forEach(this::performStockEntry);
	}
	
	private void performStockEntry(ProviderLineOrder lineOrder) {
		InventoryMovementDto invMovDto = InventoryMovementDto.builder()
				.item(itemMapper.mapTo(lineOrder.getItem()))
				.movementDate(Instant.now())
				.movementType(MovementType.ENTRY)
				.movementSource(MovementSource.PROVIDER_ORDER)
				.quantity(lineOrder.getQuantity())
				.companyId(lineOrder.getCompanyId())
				.build();
		inventoryMovementService.enterInventory(invMovDto);
	}
	
}
