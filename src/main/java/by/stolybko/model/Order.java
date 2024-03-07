package by.stolybko.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "orderid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "orderid"),
            inverseJoinColumns = @JoinColumn(name = "productid"))
    private List<Product> products;

    public Order() {
    }

    public Order(UUID id, Customer customer, LocalDateTime createDate, List<Product> products) {
        this.id = id;
        this.customer = customer;
        this.createDate = createDate;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        return Objects.equals(id, order.id) && customer.getId().equals(order.customer.getId()) && Objects.equals(createDate, order.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer.getId(), createDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customer.getId() +
                ", createDate=" + createDate +
                ", products=" + products +
                '}';
    }
}
