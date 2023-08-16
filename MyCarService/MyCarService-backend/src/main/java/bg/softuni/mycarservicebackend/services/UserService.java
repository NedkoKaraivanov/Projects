package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.dtos.UserRoleDTO;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("UserEntity with name " + email + " not found!"));
    }
    public UserDTO updateProfile(Principal principal, UserDTO userDTO) {
        UserEntity existingUser = userRepository.findByEmail(principal.getName()).orElseThrow();
        String newEmail = userDTO.getEmail();

        if (userRepository.findByEmail(newEmail).isPresent() && (!existingUser.getEmail().equals(newEmail))) {
            throw new RuntimeException();
        }

        modelMapper.map(userDTO, existingUser);
        userRepository.save(existingUser);
        return createUserDTO(existingUser);
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

}
