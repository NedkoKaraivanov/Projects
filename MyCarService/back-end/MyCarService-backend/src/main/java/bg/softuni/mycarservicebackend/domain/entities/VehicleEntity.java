package bg.softuni.mycarservicebackend.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @OneToOne()
    private UserEntity user;

    @Column
    private String brand;

    @Column
    private String model;
}
