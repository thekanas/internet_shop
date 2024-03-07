package by.stolybko.service.impl;

import by.stolybko.exception.EntityNotFoundException;
import by.stolybko.model.Product;
import by.stolybko.repository.ProductRepository;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import by.stolybko.service.mapper.ProductDtoMapper;
import by.stolybko.util.ProductTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDtoMapper productDtoMapper;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    void getByIdTest() {
        // given
        UUID uuid = ProductTestData.getProductId();
        Product product = ProductTestData.getProduct();
        ProductResponseDto expected = ProductTestData.getProductResponseDto();

        when(productRepository.findById(uuid))
                .thenReturn(Optional.of(product));
        when(productDtoMapper.toDto(product))
                .thenReturn(expected);

        // when
        ProductResponseDto actual = productService.getById(uuid);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void getByIdShouldTrowException_WhenIdNotFound() {
        // given
        UUID uuid = ProductTestData.getProductId();

        when(productRepository.findById(uuid))
                .thenReturn(Optional.empty());

        // when, then
        var exception = assertThrows(EntityNotFoundException.class, () -> productService.getById(uuid));
        assertThat(exception.getMessage())
                .isEqualTo("Entity Product with id " + uuid + " is not found");

    }

    @Test
    void deleteByIdTest() {
        // given
        UUID uuid = ProductTestData.getProductId();

        // when
        productService.deleteById(uuid);

        // then
        verify(productRepository).deleteById(uuid);
    }

    @Test
    void getAllTest() {
        // given
        Product product1 = ProductTestData.getProduct();
        Product product2 = ProductTestData.getProduct();

        ProductResponseDto productResponseDto1 = ProductTestData.getProductResponseDto();
        ProductResponseDto productResponseDto2 = ProductTestData.getProductResponseDto();

        List<Product> products = List.of(product1, product2);
        List<ProductResponseDto> expected = List.of(productResponseDto1, productResponseDto2);

        when(productRepository.findAll())
                .thenReturn(products);
        when(productDtoMapper.toDto(product1))
                .thenReturn(productResponseDto1);
        when(productDtoMapper.toDto(product2))
                .thenReturn(productResponseDto2);

        // when
        List<ProductResponseDto> actual = productService.getAll();

        // then
        assertThat(actual).containsAll(expected);

    }

    @Test
    void saveTest() {
        // given
        Product savedProduct = ProductTestData.getProduct();
        Product product = new Product(null, savedProduct.getName(), savedProduct.getPrice());
        ProductRequestDto productRequestDto = ProductTestData.getProductRequestDto();
        ProductResponseDto expected = ProductTestData.getProductResponseDto();

        when(productRepository.save(product))
                .thenReturn(savedProduct);
        when(productDtoMapper.toEntity(productRequestDto))
                .thenReturn(product);
        when(productDtoMapper.toDto(savedProduct))
                .thenReturn(expected);

        // when
        ProductResponseDto actual = productService.save(productRequestDto);

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void updateTest() {
        // given
        UUID uuid = ProductTestData.getProductId();
        String name = "New";
        BigDecimal price = BigDecimal.valueOf(1.99);
        Product updatedProduct = new Product(uuid, name, price);
        Product product = new Product(uuid, name, price);
        ProductRequestDto productRequestDto = new ProductRequestDto(name, price);
        ProductResponseDto expected = new ProductResponseDto(uuid, name, price);

        when(productRepository.save(product))
                .thenReturn(updatedProduct);
        when(productDtoMapper.toEntity(productRequestDto))
                .thenReturn(product);
        when(productDtoMapper.toDto(updatedProduct))
                .thenReturn(expected);

        // when
        ProductResponseDto actual = productService.update(uuid, productRequestDto);

        // then
        assertThat(actual).isEqualTo(expected);

    }

//    @Test
//    void updateShouldTrowException_WhenIdNotFound() {
//        // given
//        UUID uuid = ProductTestData.getProductId();
//        String name = "New";
//        BigDecimal price = BigDecimal.valueOf(1.99);
//        Product product = new Product(null, name, price);
//        ProductRequestDto productRequestDto = new ProductRequestDto(name, price);
//
//        when(productRepository.update(uuid, product))
//                .thenReturn(Optional.empty());
//
//        // when, then
//        var exception = assertThrows(EntityNotFoundException.class, () -> productService.update(uuid, productRequestDto));
//        assertThat(exception.getMessage())
//                .isEqualTo("Entity Product with id " + uuid + " is not found");
//
//    }

}
