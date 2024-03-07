package by.stolybko.model;

import by.stolybko.util.CustomerTestData;
import by.stolybko.util.OrderTestData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testEquals() {
        Order order = OrderTestData.getOrder();
        assertTrue(order.equals(order));
    }

    @Test
    void testHashCode() {
        Order order = OrderTestData.getOrder();
        assertNotNull(order.hashCode());
    }

    @Test
    void testToString() {
        Order order = OrderTestData.getOrder();
        String actual = "Order{id=5f0cba7d-643b-48c2-8713-5efc2983acfe, customerId=25486810-43dd-41e8-ab60-98aa2d200acb, createDate=2024-01-01T01:01:01, products=[Product{id=25486810-43dd-41e8-ab60-98aa2d200acb, name='SomeProduct', price=9.99}, Product{id=25486810-43dd-41e8-ab60-98aa2d200acb, name='SomeProduct', price=9.99}]}";
        String expected = order.toString();
        assertThat(actual).isEqualTo(expected);
    }
}