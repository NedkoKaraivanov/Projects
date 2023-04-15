package bg.softuni.mywarehouse.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
public class CustomerEntity extends BaseEntity {

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity customer;

    private String address;

    @Column(unique = true)
    private String phoneNumber;

    @OneToMany
    private List<OrderEntity> orders;

    public UserEntity getCustomer() {
        return customer;
    }

    public CustomerEntity setCustomer(UserEntity customer) {
        this.customer = customer;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CustomerEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public CustomerEntity setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }
}
