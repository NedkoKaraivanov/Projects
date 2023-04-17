package bg.softuni.mywarehouse.domain.dtos;

import bg.softuni.mywarehouse.domain.enums.ProductBrandEnum;
import bg.softuni.mywarehouse.domain.enums.ProductNameEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private ProductNameEnum name;

    private ProductBrandEnum brand;

    private Double size;

    private double price;

    private int quantity;
}
