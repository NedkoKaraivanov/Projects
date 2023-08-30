package bg.softuni.mycarservicebackend.domain.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Long id;
    private UserDTO user;
    private VehicleDTO vehicle;
    private String bookingDate;
    private String finishDate;
    private String description;
    private String serviceType;
    private Double price;
    private Boolean isReady;
    private Boolean isConfirmed;
}
