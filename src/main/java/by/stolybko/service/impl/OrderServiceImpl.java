package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Customer;
import by.stolybko.model.Order;
import by.stolybko.model.Product;
import by.stolybko.repository.CustomerRepository;
import by.stolybko.repository.OrderRepository;
import by.stolybko.repository.ProductRepository;
import by.stolybko.service.OrderService;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.service.mapper.OrderDtoMapper;
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
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDtoMapper orderDtoMapper, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderDtoMapper = orderDtoMapper;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
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
        Order order = new Order();
        Customer customer = customerRepository.findById(orderRequestDto.customerId())
                .orElseThrow(() -> new EntityNotFoundException(orderRequestDto.customerId(), Customer.class));
        List<Product> products = orderRequestDto.products().stream()
                .map(u -> productRepository.findById(u.id())
                        .orElseThrow(() -> new EntityNotFoundException(u.id(), Product.class)))
                .toList();

        order.setCustomer(customer);
        order.setProducts(products);
        Order savedOrder = orderRepository.save(order);

        return orderDtoMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponseDto update(UUID id, OrderRequestDto orderRequestDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Order.class));

        Customer customer = customerRepository.findById(orderRequestDto.customerId())
                .orElseThrow(() -> new EntityNotFoundException(orderRequestDto.customerId(), Customer.class));
        List<Product> products = orderRequestDto.products().stream()
                .map(u -> productRepository.findById(u.id())
                        .orElseThrow(() -> new EntityNotFoundException(u.id(), Product.class)))
                .toList();

        order.setCustomer(customer);
        order.setProducts(products);

        return orderDtoMapper.toDto(order);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }

}
