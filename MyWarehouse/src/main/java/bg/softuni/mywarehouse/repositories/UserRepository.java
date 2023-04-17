package bg.softuni.mywarehouse.repositories;

import bg.softuni.mywarehouse.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByIsActive(boolean active);

    UserEntity findByFirstNameOrLastName(String firstName, String lastName);

    UserEntity findByEmail(String email);
}
