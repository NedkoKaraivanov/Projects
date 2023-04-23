package bg.softuni.mywarehouse.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private String orderTime;

    private boolean isLoaded;

    private boolean isPaid;

    private double totalPrice;

    private List<ProductDTO> products;
}
