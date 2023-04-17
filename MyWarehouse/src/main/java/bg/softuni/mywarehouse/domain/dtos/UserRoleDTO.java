package bg.softuni.mywarehouse.domain.dtos;


import bg.softuni.mywarehouse.domain.enums.UserRoleEnum;
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
