package by.stolybko.repository.impl;

import by.stolybko.model.Customer;
import by.stolybko.repository.CustomerRepository;
import by.stolybko.repository.mapper.CustomerResultSetMapperImpl;
import by.stolybko.util.TestConstants;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerRepositoryImplTest extends BaseRepositoryTest {


    private final CustomerRepository customerRepository = new CustomerRepositoryImpl(connectionManager, new CustomerResultSetMapperImpl());


    @Test
    void findByIdTest() {

        Customer customer = customerRepository.findById(UUID.fromString("cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac")).orElseThrow();

        assertThat(customer)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", UUID.fromString("cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac"));

    }

    @Test
    void findAllTest() {

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers)
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    void saveTest() {
        Customer customer = new Customer(null, "Test", "test@test.ru");

        Optional<Customer> savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer).isPresent();
        assertThat(savedCustomer.get())
                .hasFieldOrProperty("id").isNotNull();

    }

    @Test
    void updateTest() {
        UUID uuid = UUID.fromString("cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac");

        Customer customer = new Customer(uuid, "Updated", "Updated@test.ru");

        Optional<Customer> updatedCustomer = customerRepository.update(uuid, customer);

        assertThat(updatedCustomer).isPresent();
        assertThat(updatedCustomer.get())
                .hasFieldOrPropertyWithValue("fullName","Updated")
                .hasFieldOrPropertyWithValue("email","Updated@test.ru");
    }

    @Test
    void deleteByIdShouldReturnTrue_whenUuidFound() {
        UUID uuid = UUID.fromString("cd0ebac6-2b2d-465c-ab6f-752dcd8cc7ac");

        boolean delete = customerRepository.deleteById(uuid);

        assertThat(delete).isTrue();
    }

    @Test
    void deleteByIdShouldReturnFalse_whenUuidNotFound() {

        boolean delete = customerRepository.deleteById(TestConstants.NO_FOUND_UUID);

        assertThat(delete).isFalse();
    }
}