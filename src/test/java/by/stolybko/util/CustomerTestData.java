package by.stolybko.util;

import by.stolybko.model.Customer;
import by.stolybko.model.Order;
import by.stolybko.service.dto.CustomerRequestDto;
import by.stolybko.service.dto.CustomerResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerTestData {

    private static UUID customerId = UUID.fromString("25486810-43dd-41e8-ab60-98aa2d200acb");
    private static String fullName = "Test Name Surname";
    private static String email = "test@test.com";
    private static List<Order> orders = new ArrayList<>();

    public static UUID getCustomerId() {
        return customerId;
    }

    public static Customer getCustomer() {
        return new Customer(customerId, fullName, email);
    }

    public static CustomerResponseDto getCustomerResponseDto() {
        return new CustomerResponseDto(customerId, fullName);
    }

    public static CustomerRequestDto getCustomerRequestDto() {
        return new CustomerRequestDto(fullName, email);
    }

}
