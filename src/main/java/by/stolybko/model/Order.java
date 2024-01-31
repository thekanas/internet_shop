package by.stolybko.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private UUID id;
    private UUID customerId;
    private LocalDateTime createDate;
    private List<Product> products;

    public Order() {
    }

    public Order(UUID id, UUID customerId, LocalDateTime createDate, List<Product> products) {
        this.id = id;
        this.customerId = customerId;
        this.createDate = createDate;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && customerId.equals(order.customerId) && Objects.equals(createDate, order.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, createDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", createDate=" + createDate +
                ", products=" + products +
                '}';
    }
}
