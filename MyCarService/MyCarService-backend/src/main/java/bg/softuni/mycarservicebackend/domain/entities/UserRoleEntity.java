package bg.softuni.mycarservicebackend.domain.entities;

import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
}
