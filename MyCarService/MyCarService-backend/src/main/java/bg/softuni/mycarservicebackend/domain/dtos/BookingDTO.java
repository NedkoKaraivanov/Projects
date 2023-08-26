package bg.softuni.mycarservicebackend.domain.dtos;

import bg.softuni.mycarservicebackend.domain.enums.ServiceTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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
