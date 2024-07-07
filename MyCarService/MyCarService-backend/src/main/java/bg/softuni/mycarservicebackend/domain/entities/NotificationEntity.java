package bg.softuni.mycarservicebackend.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity extends BaseEntity {

    @OneToOne
    private UserEntity user;

    @Column
    private String message;

    @Column
    private LocalDateTime time;

    @Column
    private Boolean isRead;
}
