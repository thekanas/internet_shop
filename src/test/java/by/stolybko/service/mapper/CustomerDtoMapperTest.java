package by.stolybko.service.mapper;

import by.stolybko.model.Customer;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import by.stolybko.util.CustomerTestData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerDtoMapperTest {

    private CustomerDtoMapper customerDtoMapper = Mappers.getMapper(CustomerDtoMapper.class);

    @Test
    void toEntity() {
        Customer actual = CustomerTestData.getCustomer();
        actual.setId(null);
        CustomerRequestDto customerRequestDto = CustomerTestData.getCustomerRequestDto();

        Customer expected = customerDtoMapper.toEntity(customerRequestDto);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void toDto() {
        Customer customer = CustomerTestData.getCustomer();
        CustomerResponseDto actual = CustomerTestData.getCustomerResponseDto();

        CustomerResponseDto expected = customerDtoMapper.toDto(customer);

        assertThat(actual).isEqualTo(expected);
    }
}