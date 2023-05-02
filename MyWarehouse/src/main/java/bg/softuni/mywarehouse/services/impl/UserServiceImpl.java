package bg.softuni.mywarehouse.services.impl;

import bg.softuni.mywarehouse.domain.dtos.UserRegistrationDTO;
import bg.softuni.mywarehouse.domain.entities.UserEntity;
import bg.softuni.mywarehouse.domain.entities.UserRoleEntity;
import bg.softuni.mywarehouse.domain.enums.UserRoleEnum;
import bg.softuni.mywarehouse.domain.request.UserRequest;
import bg.softuni.mywarehouse.repositories.UserRepository;
import bg.softuni.mywarehouse.repositories.UserRoleRepository;
import bg.softuni.mywarehouse.services.UserRoleService;
import bg.softuni.mywarehouse.services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, UserRoleService userRoleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
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
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("UserEntity with name " + email + " not found!"));
    }

    @Override
    public UserEntity createUser(UserRequest userRequest) {
        String hashedPwd = passwordEncoder.encode(userRequest.getPassword());
        List<UserRoleEntity> userRoles = userRoleService.createUserRoles(userRequest.getRoles());
        return userRepository.save(UserEntity.builder().email(userRequest.getEmail()).password(hashedPwd).isActive(userRequest.getIsActive())
                .firstName(userRequest.getFirstName()).lastName(userRequest.getLastName())
                .roles(userRoles).address(userRequest.getAddress()).phoneNumber(userRequest.getPhoneNumber()).build());
    }

    @Override
    public UserEntity registerUser(UserRegistrationDTO userRegistrationDTO) {
        String hashedPwd = passwordEncoder.encode(userRegistrationDTO.getPassword());
        UserRoleEntity role = userRoleRepository.findByRole(UserRoleEnum.USER);
        List<UserRoleEntity> userRoles = List.of(role);
        return userRepository.save(UserEntity.builder().email(userRegistrationDTO.getEmail()).password(hashedPwd)
                .firstName(userRegistrationDTO.getFirstName()).lastName(userRegistrationDTO.getLastName())
                .roles(userRoles).address(userRegistrationDTO.getAddress()).phoneNumber(userRegistrationDTO.getPhoneNumber()).build());
    }

    @Override
    public UserEntity updateUser(UserEntity existingUser, UserRequest userRequest) {
        String hashedPwd = passwordEncoder.encode(userRequest.getPassword());
        List<UserRoleEntity> userRoles = userRoleService.createUserRoles(userRequest.getRoles());
        existingUser.setEmail(userRequest.getEmail() == null ? existingUser.getEmail() : userRequest.getEmail());
        existingUser.setPassword(hashedPwd);
        existingUser.setIsActive(userRequest.getIsActive() == null ? existingUser.getIsActive() : userRequest.getIsActive());
        existingUser.setFirstName(userRequest.getFirstName() == null ? existingUser.getFirstName() : userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName() == null ? existingUser.getLastName() : userRequest.getLastName());
        existingUser.setRoles(userRoles);
        existingUser.setAddress(userRequest.getAddress() == null ? existingUser.getAddress() : userRequest.getAddress());
        existingUser.setPhoneNumber(userRequest.getPhoneNumber() == null ? existingUser.getPhoneNumber() : userRequest.getPhoneNumber());
        return userRepository.save(existingUser);
    }

    @Override
    public UserEntity deleteUser(Long id) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        userRepository.deleteById(id);
        return existingUser.get();
    }
}
