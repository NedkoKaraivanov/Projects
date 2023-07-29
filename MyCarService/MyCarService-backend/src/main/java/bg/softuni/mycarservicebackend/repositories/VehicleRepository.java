package bg.softuni.mycarservicebackend.repositories;

import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
}
