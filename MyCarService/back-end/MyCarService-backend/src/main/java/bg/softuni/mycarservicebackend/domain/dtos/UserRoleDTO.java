package bg.softuni.mycarservicebackend.domain.dtos;

import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {

    Long id;
    UserRoleEnum userRole;
}
