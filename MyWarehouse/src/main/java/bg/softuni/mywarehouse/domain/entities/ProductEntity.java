package bg.softuni.mywarehouse.domain.entities;

import bg.softuni.mywarehouse.domain.enums.ProductBrandEnum;
import bg.softuni.mywarehouse.domain.enums.ProductNameEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private ProductNameEnum name;

    @Enumerated(EnumType.STRING)
    private ProductBrandEnum brand;

    @Column
    private Double size;

    @Column
    private double price;

    @Column
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public ProductEntity setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductNameEnum getName() {
        return name;
    }

    public ProductEntity setName(ProductNameEnum name) {
        this.name = name;
        return this;
    }

    public ProductBrandEnum getBrand() {
        return brand;
    }

    public ProductEntity setBrand(ProductBrandEnum brand) {
        this.brand = brand;
        return this;
    }

    public Double getSize() {
        return size;
    }

    public ProductEntity setSize(Double size) {
        this.size = size;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ProductEntity setPrice(double price) {
        this.price = price;
        return this;
    }
}
