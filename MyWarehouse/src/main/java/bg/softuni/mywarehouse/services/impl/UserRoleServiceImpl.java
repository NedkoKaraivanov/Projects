package bg.softuni.mywarehouse.services.impl;

import bg.softuni.mywarehouse.domain.entities.UserRoleEntity;
import bg.softuni.mywarehouse.domain.enums.UserRoleEnum;
import bg.softuni.mywarehouse.repositories.UserRoleRepository;
import bg.softuni.mywarehouse.services.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRoleEntity> createUserRoles(List<String> roles) {
        return userRoleRepository.saveAll(roles.stream().map(role -> UserRoleEntity.builder().role(UserRoleEnum.valueOf(role)).build()).collect(Collectors.toList()));
    }
}
