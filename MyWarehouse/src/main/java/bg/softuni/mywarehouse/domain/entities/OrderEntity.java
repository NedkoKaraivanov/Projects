package bg.softuni.mywarehouse.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    private LocalDate orderDate;

    private boolean isLoaded;

    private boolean isPaid;

    private double totalPrice;

    @OneToMany
    private List<ProductEntity> products;

    public CustomerEntity getCustomer() {
        return customer;
    }

    public OrderEntity setCustomer(CustomerEntity customer) {
        this.customer = customer;
        return this;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderEntity setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public OrderEntity setLoaded(boolean loaded) {
        isLoaded = loaded;
        return this;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public OrderEntity setPaid(boolean paid) {
        isPaid = paid;
        return this;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public OrderEntity setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public OrderEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }
}
