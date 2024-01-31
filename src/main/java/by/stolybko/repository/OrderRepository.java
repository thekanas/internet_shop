package by.stolybko.repository;

import by.stolybko.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends CRUDRepository<Order, UUID>{

    Optional<Order> findByIdWithProduct(UUID id);
    List<Order> findAllByCustomerId(UUID customerId);

}
