package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Order;
import by.stolybko.repository.OrderRepository;
import by.stolybko.service.OrderService;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.service.mapper.OrderDtoMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.orderDtoMapper = Mappers.getMapper(OrderDtoMapper.class);
    }

    @Override
    public OrderResponseDto getById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
        return orderDtoMapper.toDto(order);
    }

    @Override
    public OrderResponseDtoWithProduct getByIdWithProduct(UUID id) {
        Order order = orderRepository.findByIdWithProduct(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
        return orderDtoMapper.toDtoWithProduct(order);
    }

    @Override
    public List<OrderResponseDto> getAll() {
        return orderRepository.findAll().stream()
                .map(orderDtoMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderResponseDto> getAllByCustomerId(UUID customerId) {
        return orderRepository.findAllByCustomerId(customerId).stream()
                .map(orderDtoMapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto save(OrderRequestDto orderRequestDto) {
        Order savedOrder = orderRepository.save(orderDtoMapper.toEntity(orderRequestDto)).orElseThrow();
        return orderDtoMapper.toDto(savedOrder);
    }

    @Override
    public boolean deleteById(UUID id) {
        return orderRepository.deleteById(id);
    }

    @Override
    public OrderResponseDto update(UUID id, OrderRequestDto orderRequestDto) {
        Order updatedOrder = orderRepository.update(id, orderDtoMapper.toEntity(orderRequestDto))
                .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
        return orderDtoMapper.toDto(updatedOrder);
    }
}
