package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Order;
import by.stolybko.model.Product;
import by.stolybko.repository.OrderRepository;
import by.stolybko.service.OrderService;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.service.mapper.OrderDtoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDtoMapper orderDtoMapper) {
        this.orderRepository = orderRepository;
        this.orderDtoMapper = orderDtoMapper;
    }

    @Override
    public OrderResponseDto getById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
        return orderDtoMapper.toDto(order);
    }

    @Override
    public OrderResponseDtoWithProduct getByIdWithProduct(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
        List<Product> products = order.getProducts();

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
    @Transactional
    public OrderResponseDto save(OrderRequestDto orderRequestDto) {
        Order savedOrder = orderRepository.save(orderDtoMapper.toEntity(orderRequestDto));
        return orderDtoMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderResponseDto update(UUID id, OrderRequestDto orderRequestDto) {
        Order updateOrder = orderDtoMapper.toEntity(orderRequestDto);
        updateOrder.setId(id);

        Order updatedOrder = orderRepository.save(updateOrder);
        return orderDtoMapper.toDto(updatedOrder);
    }
}
