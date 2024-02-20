package by.stolybko.util;

import by.stolybko.model.Order;
import by.stolybko.model.Product;
import by.stolybko.service.dto.OrderRequestDto;
import by.stolybko.service.dto.OrderResponseDto;
import by.stolybko.service.dto.OrderResponseDtoWithProduct;
import by.stolybko.service.dto.ProductRequestDto;
import by.stolybko.service.dto.ProductResponseDto;
import by.stolybko.service.dto.ProductUuidRequestDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderTestData {

    private static UUID orderId = UUID.fromString("5f0cba7d-643b-48c2-8713-5efc2983acfe");
    private static UUID customerId = UUID.fromString("58cf8a5b-3f98-4180-86d2-c22e033642f5");
    private static LocalDateTime createDate = LocalDateTime.of(2024, 1, 1, 1, 1, 1);
    private static List<Product> products = List.of(ProductTestData.getProduct(), ProductTestData.getProduct());
    private static List<ProductRequestDto> productsRequest = List.of(ProductTestData.getProductRequestDto(), ProductTestData.getProductRequestDto());
    private static List<ProductResponseDto> productsResponse = List.of(ProductTestData.getProductResponseDto(), ProductTestData.getProductResponseDto());
    private static List<ProductUuidRequestDto> productsUuidList = List.of(ProductTestData.getProductUuidRequestDto(), ProductTestData.getProductUuidRequestDto());

    public static UUID getOrderId() {
        return orderId;
    }

    public static UUID getCustomerId() {
        return customerId;
    }

    public static Order getOrder() {
        return new Order(orderId, customerId, createDate, products);
    }

    public static OrderResponseDto getOrderResponseDto() {
        return new OrderResponseDto(orderId, customerId, createDate);
    }

    public static OrderResponseDtoWithProduct getOrderResponseDtoWithProduct() {
        return new OrderResponseDtoWithProduct(orderId, customerId, createDate, productsResponse);
    }

    public static OrderRequestDto getOrderRequestDto() {
        return new OrderRequestDto(customerId, productsUuidList);
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static List<ProductRequestDto> getProductsRequestDtoList() {
        return productsRequest;
    }
    public static List<ProductUuidRequestDto> getProductUuidRequestDto() {
        return productsUuidList;
    }

    public static List<ProductResponseDto> getProductsResponseDto() {
        return productsResponse;
    }

}
