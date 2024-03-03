package by.stolybko.model;

import by.stolybko.util.CustomerTestData;
import by.stolybko.util.ProductTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ProductTest {

    @Test
    void testEquals() {
        Product product = ProductTestData.getProduct();
        assertTrue(product.equals(product));
    }

    @Test
    void testHashCode() {
        Product product = ProductTestData.getProduct();
        assertNotNull(product.hashCode());
    }

    @Test
    void testToString() {
        Product product = ProductTestData.getProduct();
        String actual = "Product{id=25486810-43dd-41e8-ab60-98aa2d200acb, name='SomeProduct', price=9.99}";
        String expected = product.toString();
        assertEquals(expected, actual);
    }
}