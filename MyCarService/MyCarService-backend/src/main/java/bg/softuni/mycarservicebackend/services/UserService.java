package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.dtos.ChangeEmailDTO;
import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.dtos.UserRoleDTO;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserRoleEntity;
import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import bg.softuni.mycarservicebackend.exceptions.ExistingUserException;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("UserEntity with name " + email + " not found!"));
    }
    public UserDTO updateProfile(Principal principal, UserDTO userDTO) {
        UserEntity principalUser = userRepository.findByEmail(principal.getName()).orElseThrow();
        String newEmail = userDTO.getEmail();

        if (userRepository.findByEmail(newEmail).isPresent() && (!principalUser.getEmail().equals(newEmail))) {
            throw new ExistingUserException();
        }

        modelMapper.map(userDTO, principalUser);
        userRepository.save(principalUser);

        return createUserDTO(principalUser);
    }

    public UserDTO createUserDTO(UserEntity user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles().stream().map(role -> UserRoleDTO.builder()
                        .userRole(role.getRole()).id(role.getId()).build()).collect(Collectors.toList()))
                .build();
    }

    public void initTestUsers() {
        var userRolesUser = userRoleRepository.findByRole(UserRoleEnum.USER);
        var userRolesAdmin = userRoleRepository.findByRole(UserRoleEnum.ADMIN);

        var normalUser = UserEntity.builder()
                .email("user@test.com")
                .password(passwordEncoder.encode("123123"))
                .roles(List.of(userRolesUser))
                .build();
        userRepository.save(normalUser);

        var adminUser = UserEntity.builder()
                .email("admin@test.com")
                .password(passwordEncoder.encode("123123"))
                .roles(List.of(userRolesAdmin))
                .build();
        userRepository.save(adminUser);
    }

    public void updateEmail(Principal principal, ChangeEmailDTO changeEmailDTO) {

        UserEntity existingUser = this.userRepository.findByEmail(principal.getName()).get();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        existingUser.getEmail(),
                        changeEmailDTO.getPassword()));

        if (this.userRepository.findByEmail(changeEmailDTO.getNewEmail()).isPresent()) {
            throw new ExistingUserException();
        }

        existingUser.setEmail(changeEmailDTO.getNewEmail());
        this.userRepository.save(existingUser);
    }

    protected List<UserEntity> findAdminUsers() {
        UserRoleEntity adminRole = this.userRoleRepository.findByRole(UserRoleEnum.ADMIN);
        return this.userRepository.findAllByRolesEquals(List.of(adminRole));
    }
}
