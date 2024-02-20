package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Customer;
import by.stolybko.repository.impl.CustomerRepositoryImpl;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import by.stolybko.util.CustomerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepositoryImpl customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    void getByIdTest() {
        // given
        UUID uuid = CustomerTestData.getCustomerId();
        Customer customer = CustomerTestData.getCustomer();
        CustomerResponseDto expected = CustomerTestData.getCustomerResponseDto();

        when(customerRepository.findById(uuid))
                .thenReturn(Optional.of(customer));

        // when
        CustomerResponseDto actual = customerService.getById(uuid);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void getByIdShouldTrowException_WhenIdNotFound() {
        // given
        UUID uuid = CustomerTestData.getCustomerId();

        when(customerRepository.findById(uuid))
                .thenReturn(Optional.empty());

        // when, then
        var exception = assertThrows(EntityNotFoundException.class, () -> customerService.getById(uuid));
        assertThat(exception.getMessage())
                .isEqualTo("Entity Customer with id " + uuid + " is not found");

    }

    @Test
    void deleteByIdTest() {
        // given
        UUID uuid = CustomerTestData.getCustomerId();

        // when
        customerService.deleteById(uuid);

        // then
        verify(customerRepository).deleteById(uuid);
    }

    @Test
    void getAllTest() {
        // given
        Customer customer1 = CustomerTestData.getCustomer();
        Customer customer2 = CustomerTestData.getCustomer();

        CustomerResponseDto customerResponseDto1 = CustomerTestData.getCustomerResponseDto();
        CustomerResponseDto customerResponseDto2 = CustomerTestData.getCustomerResponseDto();

        List<Customer> customers = List.of(customer1, customer2);
        List<CustomerResponseDto> expected = List.of(customerResponseDto1, customerResponseDto2);

        when(customerRepository.findAll())
                .thenReturn(customers);

        // when
        List<CustomerResponseDto> actual = customerService.getAll();

        // then
        assertThat(actual).containsAll(expected);

    }

    @Test
    void saveTest() {
        // given
        Customer savedCustomer = CustomerTestData.getCustomer();
        Customer customer = new Customer(null, savedCustomer.getFullName(), savedCustomer.getEmail());
        CustomerRequestDto customerRequestDto = CustomerTestData.getCustomerRequestDto();
        CustomerResponseDto expected = CustomerTestData.getCustomerResponseDto();

        when(customerRepository.save(customer))
                .thenReturn(Optional.of(savedCustomer));

        // when
        CustomerResponseDto actual = customerService.save(customerRequestDto);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void updateTest() {
        // given
        UUID uuid = CustomerTestData.getCustomerId();
        Customer updatedCustomer = new Customer(uuid, "New", "new@new.com");
        Customer customer = new Customer(null, "New", "new@new.com");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("New", "new@new.com");
        CustomerResponseDto expected = new CustomerResponseDto(uuid, "New");

        when(customerRepository.update(uuid, customer))
                .thenReturn(Optional.of(updatedCustomer));

        // when
        CustomerResponseDto actual = customerService.update(uuid, customerRequestDto);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void updateShouldTrowException_WhenIdNotFound() {
        // given
        UUID uuid = CustomerTestData.getCustomerId();
        Customer customer = new Customer(null, "New", "new@new.com");
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("New", "new@new.com");

        when(customerRepository.update(uuid, customer))
                .thenReturn(Optional.empty());

        // when, then
        var exception = assertThrows(EntityNotFoundException.class, () -> customerService.update(uuid, customerRequestDto));
        assertThat(exception.getMessage())
                .isEqualTo("Entity Customer with id " + uuid + " is not found");

    }

}