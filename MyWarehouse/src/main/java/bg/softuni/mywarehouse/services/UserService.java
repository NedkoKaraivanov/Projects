package bg.softuni.mywarehouse.services;

import bg.softuni.mywarehouse.domain.dtos.UserDTO;
import bg.softuni.mywarehouse.domain.entities.OrderEntity;
import bg.softuni.mywarehouse.domain.entities.UserEntity;
import bg.softuni.mywarehouse.domain.request.UserRequest;

import java.util.List;

public interface UserService {

    List<UserEntity> getAllUsers();

    UserEntity getUserById(Long id);

    List<UserEntity> getAllActiveUsers(boolean active);

    UserEntity getUserByFirstNameOrLastName(String firstName, String lastName);

    UserEntity getUserByEmail(String email);

    UserEntity createUser(UserRequest userRequest);

    UserEntity updateUser(UserEntity existingUser, UserRequest userRequest);

    void deleteUser(Long id);
}
