package bg.softuni.mywarehouse.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products_info")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoEntity extends BaseEntity {

    @OneToOne(mappedBy = "productInfo", targetEntity = ProductEntity.class)
    private ProductEntity product;

    private int quantityInStock;
}
