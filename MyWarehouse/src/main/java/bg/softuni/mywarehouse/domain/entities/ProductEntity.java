package bg.softuni.mywarehouse.domain.entities;

import bg.softuni.mywarehouse.domain.enums.ProductBrandEnum;
import bg.softuni.mywarehouse.domain.enums.ProductNameEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "info_id", referencedColumnName = "id")
    private ProductInfoEntity productInfo;
}
