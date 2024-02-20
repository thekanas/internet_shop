package by.stolybko.model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Customer extends BaseEntity<UUID> {

    private UUID id;
    private String fullName;
    private String email;
    private List<Order> orders;

    public Customer() {
    }

    public Customer(UUID id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && fullName.equals(customer.fullName) && email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
