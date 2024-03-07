package by.stolybko.repository;

import by.stolybko.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    //Optional<Order> findByIdWithProduct(UUID id);
    List<Order> findAllByCustomerId(UUID customerId);

}
