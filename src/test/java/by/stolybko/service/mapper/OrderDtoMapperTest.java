package by.stolybko.service.mapper;

import by.stolybko.model.Order;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.util.OrderTestData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderDtoMapperTest {

    private OrderDtoMapper orderDtoMapper = Mappers.getMapper(OrderDtoMapper.class);

    @Test
    void toDtoWithProduct() {
        Order order = OrderTestData.getOrder();
        OrderResponseDtoWithProduct actual = OrderTestData.getOrderResponseDtoWithProduct();

        OrderResponseDtoWithProduct expected = orderDtoMapper.toDtoWithProduct(order);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void toDto() {
        Order order = OrderTestData.getOrder();
        OrderResponseDto actual = OrderTestData.getOrderResponseDto();

        OrderResponseDto expected = orderDtoMapper.toDto(order);

        assertThat(actual).isEqualTo(expected);
    }

}