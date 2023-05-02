package bg.softuni.mywarehouse.repositories;

import bg.softuni.mywarehouse.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByIsActive(boolean active);

    UserEntity findByFirstNameOrLastName(String firstName, String lastName);

    Optional<UserEntity> findByEmail(String email);

    void deleteById(Long id);
}
