package bg.softuni.mycarservicebackend.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phoneNumber;

    @Column
    private Boolean isActive;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<UserRoleEntity> roles;

    @OneToMany(targetEntity = VehicleEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<VehicleEntity> vehicles;

    @OneToMany(targetEntity = BookingEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BookingEntity> bookings;
}
