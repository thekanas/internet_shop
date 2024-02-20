package by.stolybko.util;

import by.stolybko.model.Product;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import by.stolybko.service.dto.ProductUuidRequestDto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductTestData {

    private static UUID productId = UUID.fromString("25486810-43dd-41e8-ab60-98aa2d200acb");
    private static String name = "SomeProduct";
    private static BigDecimal price = BigDecimal.valueOf(9.99);

    public static UUID getProductId() {
        return productId;
    }

    public static Product getProduct() {
        return new Product(productId, name, price);
    }

    public static ProductResponseDto getProductResponseDto() {
        return new ProductResponseDto(productId, name, price);
    }

    public static ProductRequestDto getProductRequestDto() {
        return new ProductRequestDto(name, price);
    }

    public static ProductUuidRequestDto getProductUuidRequestDto() {
        return new ProductUuidRequestDto(productId);
    }

}
