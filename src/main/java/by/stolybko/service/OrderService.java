package by.stolybko.service;

import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;

import java.util.List;
import java.util.UUID;

public interface OrderService extends CRUDService<OrderResponseDto, OrderRequestDto>{

    OrderResponseDtoWithProduct getByIdWithProduct(UUID id);
    List<OrderResponseDto> getAllByCustomerId(UUID customerId);

}
