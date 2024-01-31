package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Customer;
import by.stolybko.repository.CustomerRepository;
import by.stolybko.service.CustomerService;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import by.stolybko.service.mapper.CustomerDtoMapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerDtoMapper = Mappers.getMapper(CustomerDtoMapper.class);
    }

    @Override
    public CustomerResponseDto getById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));
        return customerDtoMapper.toDto(customer);
    }

    @Override
    public boolean deleteById(UUID id) {
        return customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerResponseDto> getAll() {
        return customerRepository.findAll().stream()
                .map(customerDtoMapper::toDto)
                .toList();
    }

    @Override
    public CustomerResponseDto save(CustomerRequestDto customerRequestDto) {
        Customer savedCustomer = customerRepository.save(customerDtoMapper.toEntity(customerRequestDto))
                .orElseThrow();
        return customerDtoMapper.toDto(savedCustomer);
    }

    @Override
    public CustomerResponseDto update(UUID id, CustomerRequestDto customerRequestDto) {
        Customer updatedCustomer = customerRepository.update(id, customerDtoMapper.toEntity(customerRequestDto))
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));
        return customerDtoMapper.toDto(updatedCustomer);
    }
}
