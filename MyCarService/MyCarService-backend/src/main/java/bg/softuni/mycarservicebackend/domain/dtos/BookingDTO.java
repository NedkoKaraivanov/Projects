package bg.softuni.mycarservicebackend.domain.dtos;

import bg.softuni.mycarservicebackend.domain.enums.ServiceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Long id;
    private UserDTO user;
    private VehicleDTO vehicle;
    private LocalDate bookDate;
    private LocalDate finishDate;
    private String description;
    private String serviceType;
    private Double price;
    private Boolean isReady;
}
