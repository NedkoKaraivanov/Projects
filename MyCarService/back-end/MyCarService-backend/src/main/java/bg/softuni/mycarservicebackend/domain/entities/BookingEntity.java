package bg.softuni.mycarservicebackend.domain.entities;

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

    @OneToOne()
    private VehicleEntity vehicle;

    @Column
    private LocalDate bookDate;

    @Column
    private LocalDate finishDate;

    @OneToOne()
    private ServiceInfoEntity serviceInfo;
}
