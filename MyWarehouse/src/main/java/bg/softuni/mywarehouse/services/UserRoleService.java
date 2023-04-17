package bg.softuni.mywarehouse.services;

import bg.softuni.mywarehouse.domain.entities.UserRoleEntity;

import java.util.List;

public interface UserRoleService {

    List<UserRoleEntity> createUserRoles(List<String> roles);

    void initRoles();
}
