package by.stolybko.service.mapper;

import by.stolybko.model.Product;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import by.stolybko.util.ProductTestData;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDtoMapperTest {

    private ProductDtoMapper productDtoMapper = Mappers.getMapper(ProductDtoMapper.class);

    @Test
    void toEntity() {
        Product actual = ProductTestData.getProduct();
        actual.setId(null);
        ProductRequestDto productRequestDto = ProductTestData.getProductRequestDto();

        Product expected = productDtoMapper.toEntity(productRequestDto);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void toDto() {
        Product product = ProductTestData.getProduct();
        ProductResponseDto actual = ProductTestData.getProductResponseDto();

        ProductResponseDto expected = productDtoMapper.toDto(product);

        assertThat(actual).isEqualTo(expected);
    }
}