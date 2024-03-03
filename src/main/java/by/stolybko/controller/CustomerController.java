package by.stolybko.controller;

import by.stolybko.service.CustomerService;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomer() {
        List<CustomerResponseDto> customers = customerService.getAll();
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerResponseDto> getCustomerByUuid(@PathVariable UUID uuid) {
        CustomerResponseDto customer = customerService.getById(uuid);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        CustomerResponseDto customer = customerService.save(customerRequestDto);
        return ResponseEntity.status(201).body(customer);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable UUID uuid, @RequestBody CustomerRequestDto customerRequestDto) {
        CustomerResponseDto customer = customerService.update(uuid, customerRequestDto);
        return ResponseEntity.ok().body(customer);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCustomerByUuid(@PathVariable UUID uuid) {
        customerService.deleteById(uuid);
        return ResponseEntity.ok().build();
    }

}
