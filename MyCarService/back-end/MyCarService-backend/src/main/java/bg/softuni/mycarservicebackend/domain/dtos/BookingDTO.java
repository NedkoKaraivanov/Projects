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
    private String bookDate;
    private String finishDate;
    private String description;
    private ProcedureInfoDto procedureInfo;
}
