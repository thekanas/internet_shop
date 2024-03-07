package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Customer;
import by.stolybko.repository.CustomerRepository;
import by.stolybko.service.CustomerService;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import by.stolybko.service.mapper.CustomerDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerDtoMapper customerDtoMapper) {
        this.customerRepository = customerRepository;
        this.customerDtoMapper = customerDtoMapper;
    }

    @Override
    public CustomerResponseDto getById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Customer.class));
        return customerDtoMapper.toDto(customer);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerResponseDto> getAll() {
        return customerRepository.findAll().stream()
                .map(customerDtoMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CustomerResponseDto save(CustomerRequestDto customerRequestDto) {
        Customer savedCustomer = customerRepository.save(customerDtoMapper.toEntity(customerRequestDto));
        return customerDtoMapper.toDto(savedCustomer);
    }

    @Override
    @Transactional
    public CustomerResponseDto update(UUID id, CustomerRequestDto customerRequestDto) {
        Customer updateCustomer = customerDtoMapper.toEntity(customerRequestDto);
        updateCustomer.setId(id);

        Customer updatedCustomer = customerRepository.save(updateCustomer);
        return customerDtoMapper.toDto(updatedCustomer);
    }
}
