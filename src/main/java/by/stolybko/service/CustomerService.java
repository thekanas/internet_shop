package by.stolybko.service;

import by.stolybko.model.Customer;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;

import java.util.UUID;

public interface CustomerService extends CRUDService<CustomerResponseDto, CustomerRequestDto> {

}
