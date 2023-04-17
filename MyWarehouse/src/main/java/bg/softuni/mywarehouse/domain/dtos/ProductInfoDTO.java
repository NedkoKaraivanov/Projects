package bg.softuni.mywarehouse.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDTO {

    private ProductDTO productDTO;

    private int in_stock;
}
