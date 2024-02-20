package by.stolybko.service.mapper;

import by.stolybko.model.Order;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDtoMapper {

    Order toEntity(OrderRequestDto orderRequestDto);

    OrderResponseDto toDto(Order order);

    @Mapping(target = "products", source = "order.products")
    OrderResponseDtoWithProduct toDtoWithProduct(Order order);

}
