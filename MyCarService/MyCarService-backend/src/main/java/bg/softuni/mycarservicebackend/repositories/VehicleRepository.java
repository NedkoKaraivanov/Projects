package bg.softuni.mycarservicebackend.repositories;

import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    List<VehicleEntity> findAllByUser(UserEntity userEntity);

    VehicleEntity findByUserAndId(UserEntity userEntity, Long id);

    boolean deleteByUserAndId(UserEntity userEntity, Long id);
}
