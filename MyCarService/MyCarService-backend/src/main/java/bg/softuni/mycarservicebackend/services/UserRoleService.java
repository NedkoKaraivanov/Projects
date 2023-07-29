package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.entities.UserRoleEntity;
import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import bg.softuni.mycarservicebackend.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public void initRoles() {
        if (this.userRoleRepository.count() == 0) {
            List<UserRoleEntity> roles = Arrays.stream(UserRoleEnum.values())
                    .map(roleEnum -> UserRoleEntity.builder().role(roleEnum).build())
                    .collect(Collectors.toList());

            this.userRoleRepository.saveAll(roles);
        }
    }
}
