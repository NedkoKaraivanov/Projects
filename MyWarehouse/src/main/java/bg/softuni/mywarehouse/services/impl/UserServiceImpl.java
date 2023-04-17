package bg.softuni.mywarehouse.services.impl;

import bg.softuni.mywarehouse.domain.entities.OrderEntity;
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
        return null;
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
    public UserEntity createUser(UserRequest user) {
        String hashedPwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        List<UserRoleEntity> userRoles = userRoleService.createUserRoles(user.getRoles());
        return userRepository.save(UserEntity.builder().email(user.getEmail()).password(hashedPwd).isActive(user.getIsActive())
                .firstName(user.getFirstName()).lastName(user.getLastName())
                .roles(userRoles).address(user.getAddress()).phoneNumber(user.getPhoneNumber()).build());
    }

    @Override
    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
