package bg.softuni.mycarservicebackend.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;

    private List<UserRoleDTO> roles;
    private List<VehicleDTO> vehicles;
    private List<BookingDTO> bookings;
}
