package by.stolybko.service.mapper;

import by.stolybko.model.Product;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductDtoMapper {

    Product toEntity(ProductRequestDto productRequestDto);

    ProductResponseDto toDto(Product product);

}
