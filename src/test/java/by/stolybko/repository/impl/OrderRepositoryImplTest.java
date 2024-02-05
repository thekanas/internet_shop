package by.stolybko.repository.impl;


import by.stolybko.model.Order;
import by.stolybko.model.Product;
import by.stolybko.repository.OrderRepository;
import by.stolybko.repository.ProductRepository;
import by.stolybko.repository.mapper.OrderResultSetMapperImpl;
import by.stolybko.repository.mapper.ProductResultSetMapperImpl;
import by.stolybko.util.ProductTestData;
import by.stolybko.util.TestConstants;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class OrderRepositoryImplTest extends BaseRepositoryTest {

    private final OrderRepository orderRepository = new OrderRepositoryImpl(connectionManager, new OrderResultSetMapperImpl());
    private final ProductRepository productRepository = new ProductRepositoryImpl(connectionManager, new ProductResultSetMapperImpl());

    @Test
    void findByIdTest() {

        Order order = orderRepository.findById(UUID.fromString("da4970c5-5053-4b48-87db-32693abe6e76")).orElseThrow();

        assertThat(order)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", UUID.fromString("da4970c5-5053-4b48-87db-32693abe6e76"));

    }

    @Test
    void findByIdWithProductTest() {

        Order order = orderRepository.findByIdWithProduct(UUID.fromString("73839edc-0373-440c-a3e9-484090fc4a0d")).orElseThrow();

        assertThat(order)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", UUID.fromString("73839edc-0373-440c-a3e9-484090fc4a0d"));

        assertThat(order.getProducts()).hasSize(2);

    }

    @Test
    void findAllTest() {

        List<Order> orders = orderRepository.findAll();

        assertThat(orders)
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    void findAllByCustomerIdTest() {

        UUID customerUuid = UUID.fromString("cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac");
        List<Order> orders = orderRepository.findAllByCustomerId(customerUuid);

        assertThat(orders)
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    void saveTest() {
        Product product1 = productRepository.findById(UUID.fromString("432c2ccf-91ba-436b-beea-694fd8ac12d9")).get();
        Product product2 = productRepository.findById(UUID.fromString("8bcbdbb4-4608-4953-a087-4882c3ac673c")).get();


        Order order = new Order(null, UUID.fromString("cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac"), null, List.of(product1, product2));

        Optional<Order> savedOrder = orderRepository.save(order);

        assertThat(savedOrder).isPresent();
        assertThat(savedOrder.get())
                .hasFieldOrProperty("id").isNotNull()
                .hasFieldOrProperty("createDate").isNotNull();

    }

    @Test
    void updateTest() {
        UUID uuid = UUID.fromString("da4970c5-5053-4b48-87db-32693abe6e76");
        UUID customerUuid = UUID.fromString("cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac");
        Product product1 = productRepository.findById(UUID.fromString("432c2ccf-91ba-436b-beea-694fd8ac12d9")).get();
        Order order = new Order(uuid, customerUuid, LocalDateTime.MIN, List.of(product1));

        Optional<Order> updatedOrder = orderRepository.update(uuid, order);

        assertThat(updatedOrder).isPresent();
        assertThat(updatedOrder.get())
                .hasFieldOrPropertyWithValue("id", uuid)
                .hasFieldOrPropertyWithValue("customerId", customerUuid);
    }

    @Test
    void deleteByIdShouldReturnTrue_whenUuidFound() {
        UUID uuid = UUID.fromString("da4970c5-5053-4b48-87db-32693abe6e76");

        boolean delete = orderRepository.deleteById(uuid);

        assertThat(delete).isTrue();
    }

    @Test
    void deleteByIdShouldReturnFalse_whenUuidNotFound() {

        boolean delete = orderRepository.deleteById(TestConstants.NO_FOUND_UUID);

        assertThat(delete).isFalse();
    }
    
}