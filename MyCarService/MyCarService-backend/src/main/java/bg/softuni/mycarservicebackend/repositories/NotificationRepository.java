package bg.softuni.mycarservicebackend.repositories;

import bg.softuni.mycarservicebackend.domain.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
