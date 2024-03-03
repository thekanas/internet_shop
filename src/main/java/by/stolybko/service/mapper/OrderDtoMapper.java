package by.stolybko.service.mapper;

import by.stolybko.model.Order;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDtoMapper {

    @Mapping(target = "customerId", source = "customer.id")
    OrderResponseDto toDto(Order order);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "products", source = "order.products")
    OrderResponseDtoWithProduct toDtoWithProduct(Order order);

}
