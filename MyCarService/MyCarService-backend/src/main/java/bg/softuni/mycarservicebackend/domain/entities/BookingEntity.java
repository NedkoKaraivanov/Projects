package bg.softuni.mycarservicebackend.domain.entities;

import bg.softuni.mycarservicebackend.domain.enums.ServiceTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity extends BaseEntity{

    @ManyToOne()
    private UserEntity user;

    @ManyToOne()
    private VehicleEntity vehicle;

    @Column
    private LocalDate bookDate;

    @Column
    private LocalDate finishDate;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private ServiceTypeEnum serviceType;

    @Column
    private Double price;

    @Column
    private Boolean isReady = false;

    @Column
    private Boolean isConfirmed = false;
}
