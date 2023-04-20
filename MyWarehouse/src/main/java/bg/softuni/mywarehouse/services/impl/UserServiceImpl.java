package bg.softuni.mywarehouse.services.impl;

import bg.softuni.mywarehouse.domain.dtos.UserDTO;
import bg.softuni.mywarehouse.domain.entities.UserEntity;
import bg.softuni.mywarehouse.domain.entities.UserRoleEntity;
import bg.softuni.mywarehouse.domain.request.UserRequest;
import bg.softuni.mywarehouse.repositories.UserRepository;
import bg.softuni.mywarehouse.services.UserRoleService;
import bg.softuni.mywarehouse.services.UserService;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserEntity> getAllActiveUsers(boolean active) {
        return userRepository.findAllByIsActive(active);
    }

    @Override
    public UserEntity getUserByFirstNameOrLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameOrLastName(firstName, lastName);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity createUser(UserRequest userRequest) {
        String hashedPwd = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
        List<UserRoleEntity> userRoles = userRoleService.createUserRoles(userRequest.getRoles());
        return userRepository.save(UserEntity.builder().email(userRequest.getEmail()).password(hashedPwd).isActive(userRequest.getIsActive())
                .firstName(userRequest.getFirstName()).lastName(userRequest.getLastName())
                .roles(userRoles).address(userRequest.getAddress()).phoneNumber(userRequest.getPhoneNumber()).build());
    }

    @Override
    public UserEntity updateUser(UserEntity existingUser, UserRequest userRequest) {
        String hashedPwd = BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt());
        List<UserRoleEntity> userRoles = userRoleService.createUserRoles(userRequest.getRoles());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(hashedPwd);
        existingUser.setIsActive(userRequest.getIsActive());
        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());
        existingUser.setRoles(userRoles);
        existingUser.setAddress(userRequest.getAddress());
        existingUser.setPhoneNumber(userRequest.getPhoneNumber());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
