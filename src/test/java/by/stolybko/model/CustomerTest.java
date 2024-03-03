package by.stolybko.model;

import by.stolybko.util.CustomerTestData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testEquals() {
        Customer customer = CustomerTestData.getCustomer();
        assertTrue(customer.equals(customer));
    }

    @Test
    void testHashCode() {
        Customer customer = CustomerTestData.getCustomer();
        assertNotNull(customer.hashCode());
    }

    @Test
    void testToString() {

        Customer customer = CustomerTestData.getCustomer();
        String actual = "Customer{id=25486810-43dd-41e8-ab60-98aa2d200acb, fullName='Test Name Surname', email='test@test.com'}";
        String expected = customer.toString();
        assertThat(actual).isEqualTo(expected);
    }
}