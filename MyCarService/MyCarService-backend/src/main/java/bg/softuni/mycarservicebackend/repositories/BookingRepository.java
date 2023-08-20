package bg.softuni.mycarservicebackend.repositories;

import bg.softuni.mycarservicebackend.domain.entities.BookingEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findAllByUser(UserEntity userEntity);
}
