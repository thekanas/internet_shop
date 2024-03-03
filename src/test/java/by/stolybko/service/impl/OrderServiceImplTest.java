package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Customer;
import by.stolybko.model.Order;
import by.stolybko.model.Product;
import by.stolybko.repository.CustomerRepository;
import by.stolybko.repository.OrderRepository;
import by.stolybko.repository.ProductRepository;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.service.dto.ProductUuidRequestDto;
import by.stolybko.service.mapper.OrderDtoMapper;
import by.stolybko.util.CustomerTestData;
import by.stolybko.util.OrderTestData;
import by.stolybko.util.ProductTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderDtoMapper orderDtoMapper;

    @InjectMocks
    private OrderServiceImpl orderService;


    @Test
    void getByIdTest() {
        // given
        UUID uuid = OrderTestData.getOrderId();
        Order order = OrderTestData.getOrder();
        OrderResponseDto expected = OrderTestData.getOrderResponseDto();

        when(orderRepository.findById(uuid))
                .thenReturn(Optional.of(order));
        when(orderDtoMapper.toDto(order))
                .thenReturn(expected);

        // when
        OrderResponseDto actual = orderService.getById(uuid);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void getByIdWithProductTest() {
        // given
        UUID uuid = OrderTestData.getOrderId();
        Order order = OrderTestData.getOrder();
        OrderResponseDtoWithProduct expected = OrderTestData.getOrderResponseDtoWithProduct();

        when(orderRepository.findById(uuid))
                .thenReturn(Optional.of(order));
        when(orderDtoMapper.toDtoWithProduct(order))
                .thenReturn(expected);

        // when
        OrderResponseDtoWithProduct actual = orderService.getByIdWithProduct(uuid);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void getByIdShouldTrowException_WhenIdNotFound() {
        // given
        UUID uuid = OrderTestData.getOrderId();

        when(orderRepository.findById(uuid))
                .thenReturn(Optional.empty());

        // when, then
        var exception = assertThrows(EntityNotFoundException.class, () -> orderService.getById(uuid));
        assertThat(exception.getMessage())
                .isEqualTo("Entity Order with id " + uuid + " is not found");

    }

    @Test
    void deleteByIdTest() {
        // given
        UUID uuid = OrderTestData.getOrderId();

        // when
        orderService.deleteById(uuid);

        // then
        verify(orderRepository).deleteById(uuid);
    }

    @Test
    void getAllTest() {
        // given
        Order order1 = OrderTestData.getOrder();
        Order order2 = OrderTestData.getOrder();

        OrderResponseDto orderResponseDto1 = OrderTestData.getOrderResponseDto();
        OrderResponseDto orderResponseDto2 = OrderTestData.getOrderResponseDto();

        List<Order> orders = List.of(order1, order2);
        List<OrderResponseDto> expected = List.of(orderResponseDto1, orderResponseDto2);

        when(orderRepository.findAll())
                .thenReturn(orders);
        when(orderDtoMapper.toDto(order1))
                .thenReturn(orderResponseDto1);
        when(orderDtoMapper.toDto(order2))
                .thenReturn(orderResponseDto2);

        // when
        List<OrderResponseDto> actual = orderService.getAll();

        // then
        assertThat(actual).containsAll(expected);

    }

    @Test
    void saveTest() {
        // given
        Order savedOrder = OrderTestData.getOrder();
        Order order = new Order(null, savedOrder.getCustomer(), null, savedOrder.getProducts());
        OrderRequestDto orderRequestDto = OrderTestData.getOrderRequestDto();
        OrderResponseDto expected = OrderTestData.getOrderResponseDto();

        when(orderRepository.save(order))
                .thenReturn(savedOrder);
        when(customerRepository.findById(any()))
                .thenReturn(Optional.ofNullable(savedOrder.getCustomer()));
        when(productRepository.findById(any()))
                .thenReturn(Optional.of(ProductTestData.getProduct()));

        when(orderDtoMapper.toDto(savedOrder))
                .thenReturn(expected);

        // when
        OrderResponseDto actual = orderService.save(orderRequestDto);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void updateTest() {
        // given
        UUID uuid = OrderTestData.getOrderId();
        Customer customer = CustomerTestData.getCustomer();
        List<Product> products = OrderTestData.getProducts();
        List<ProductUuidRequestDto> productUuidRequestDto = OrderTestData.getProductUuidRequestDto();

        Order updatedOrder = new Order(uuid, customer, LocalDateTime.MIN, products);
        Order order = new Order(null, customer, null, products);
        OrderRequestDto orderRequestDto = new OrderRequestDto(customer.getId(), productUuidRequestDto);
        OrderResponseDto expected = new OrderResponseDto(uuid, customer.getId(), LocalDateTime.MIN);

        when(orderRepository.findById(uuid))
                .thenReturn(Optional.of(order));
        when(customerRepository.findById(any()))
                .thenReturn(Optional.ofNullable(updatedOrder.getCustomer()));
        when(productRepository.findById(any()))
                .thenReturn(Optional.of(ProductTestData.getProduct()));
        when(orderDtoMapper.toDto(order))
                .thenReturn(expected);

        // when
        OrderResponseDto actual = orderService.update(uuid, orderRequestDto);

        // then
        assertThat(actual).isEqualTo(expected);

    }

//    @Test
//    void updateShouldTrowException_WhenIdNotFound() {
//        // given
//        UUID uuid = OrderTestData.getOrderId();
//        UUID customerUuid = OrderTestData.getCustomerId();
//        List<Product> products = OrderTestData.getProducts();
//        List<ProductUuidRequestDto> productUuidRequestDto = OrderTestData.getProductUuidRequestDto();
//
//        Order order = new Order(null, customerUuid, null, products);
//        OrderRequestDto orderRequestDto = new OrderRequestDto(customerUuid, productUuidRequestDto);
//
//        when(orderRepository.update(uuid, order))
//                .thenReturn(Optional.empty());
//
//        // when, then
//        var exception = assertThrows(EntityNotFoundException.class, () -> orderService.update(uuid, orderRequestDto));
//        assertThat(exception.getMessage())
//                .isEqualTo("Entity Order with id " + uuid + " is not found");
//
//    }

}
