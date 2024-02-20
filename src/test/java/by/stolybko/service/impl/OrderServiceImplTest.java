package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Order;
import by.stolybko.model.Product;
import by.stolybko.repository.impl.OrderRepositoryImpl;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductUuidRequestDto;
import by.stolybko.util.OrderTestData;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepositoryImpl orderRepository;

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

        when(orderRepository.findByIdWithProduct(uuid))
                .thenReturn(Optional.of(order));

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

        // when
        List<OrderResponseDto> actual = orderService.getAll();

        // then
        assertThat(actual).containsAll(expected);

    }

    @Test
    void saveTest() {
        // given
        Order savedOrder = OrderTestData.getOrder();
        Order order = new Order(null, savedOrder.getCustomerId(), null, savedOrder.getProducts());
        OrderRequestDto orderRequestDto = OrderTestData.getOrderRequestDto();
        OrderResponseDto expected = OrderTestData.getOrderResponseDto();

        when(orderRepository.save(order))
                .thenReturn(Optional.of(savedOrder));

        // when
        OrderResponseDto actual = orderService.save(orderRequestDto);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void updateTest() {
        // given
        UUID uuid = OrderTestData.getOrderId();
        UUID customerUuid = OrderTestData.getCustomerId();
        List<Product> products = OrderTestData.getProducts();
        List<ProductUuidRequestDto> productUuidRequestDto = OrderTestData.getProductUuidRequestDto();

        Order updatedOrder = new Order(uuid, customerUuid, LocalDateTime.MIN, products);
        Order order = new Order(null, customerUuid, null, products);
        OrderRequestDto orderRequestDto = new OrderRequestDto(customerUuid, productUuidRequestDto);
        OrderResponseDto expected = new OrderResponseDto(uuid, customerUuid, LocalDateTime.MIN);

        when(orderRepository.update(uuid, order))
                .thenReturn(Optional.of(updatedOrder));

        // when
        OrderResponseDto actual = orderService.update(uuid, orderRequestDto);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void updateShouldTrowException_WhenIdNotFound() {
        // given
        UUID uuid = OrderTestData.getOrderId();
        UUID customerUuid = OrderTestData.getCustomerId();
        List<Product> products = OrderTestData.getProducts();
        List<ProductUuidRequestDto> productUuidRequestDto = OrderTestData.getProductUuidRequestDto();

        Order order = new Order(null, customerUuid, null, products);
        OrderRequestDto orderRequestDto = new OrderRequestDto(customerUuid, productUuidRequestDto);

        when(orderRepository.update(uuid, order))
                .thenReturn(Optional.empty());

        // when, then
        var exception = assertThrows(EntityNotFoundException.class, () -> orderService.update(uuid, orderRequestDto));
        assertThat(exception.getMessage())
                .isEqualTo("Entity Order with id " + uuid + " is not found");

    }

}
