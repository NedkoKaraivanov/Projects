package bg.softuni.mycarservicebackend.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "vehicles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity extends BaseEntity {

    @ManyToOne
    private UserEntity user;

    @Column
    private String brand;

    @Column
    private String model;
}
