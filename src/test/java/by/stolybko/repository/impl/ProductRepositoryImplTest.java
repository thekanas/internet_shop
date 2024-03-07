/*
package by.stolybko.repository.impl;


import by.stolybko.model.Product;
import by.stolybko.repository.ProductRepository;
import by.stolybko.repository.mapper.ProductResultSetMapperImpl;
import by.stolybko.util.TestConstants;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class ProductRepositoryImplTest extends BaseRepositoryTest {

    private final ProductRepository productRepository = new ProductRepositoryImpl(connectionManager, new ProductResultSetMapperImpl());


    @Test
    void findByIdTest() {

        Product product = productRepository.findById(UUID.fromString("432c2ccf-91ba-436b-beea-694fd8ac12d9")).orElseThrow();

        assertThat(product)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", UUID.fromString("432c2ccf-91ba-436b-beea-694fd8ac12d9"));

    }

    @Test
    void findAllTest() {

        List<Product> products = productRepository.findAll();

        assertThat(products)
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    void saveTest() {
        Product product = new Product(null, "Test", BigDecimal.TEN);

        Optional<Product> savedProduct = productRepository.save(product);

        assertThat(savedProduct).isPresent();
        assertThat(savedProduct.get())
                .hasFieldOrProperty("id").isNotNull();

    }

    @Test
    void updateTest() {
        UUID uuid = UUID.fromString("432c2ccf-91ba-436b-beea-694fd8ac12d9");

        Product product = new Product(uuid, "Updated", BigDecimal.TEN);

        Optional<Product> updatedProduct = productRepository.update(uuid, product);

        assertThat(updatedProduct).isPresent();
        assertThat(updatedProduct.get())
                .hasFieldOrPropertyWithValue("name", "Updated")
                .hasFieldOrPropertyWithValue("price", BigDecimal.TEN);
    }

    @Test
    void deleteByIdShouldReturnTrue_whenUuidFound() {
        UUID uuid = UUID.fromString("432c2ccf-91ba-436b-beea-694fd8ac12d9");

        boolean delete = productRepository.deleteById(uuid);

        assertThat(delete).isTrue();
    }

    @Test
    void deleteByIdShouldReturnFalse_whenUuidNotFound() {

        boolean delete = productRepository.deleteById(TestConstants.NO_FOUND_UUID);

        assertThat(delete).isFalse();
    }

}*/
