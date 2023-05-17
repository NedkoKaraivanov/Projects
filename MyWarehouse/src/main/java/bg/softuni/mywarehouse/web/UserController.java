package bg.softuni.mywarehouse.web;

import bg.softuni.mywarehouse.domain.dtos.UserDTO;
import bg.softuni.mywarehouse.domain.dtos.UserRoleDTO;
import bg.softuni.mywarehouse.domain.entities.UserEntity;
import bg.softuni.mywarehouse.domain.request.UserRequest;
import bg.softuni.mywarehouse.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/admin/users")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers().stream().map(userService::createUserDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserEntity userEntity = userService.getUserById(id);

        if (userEntity != null) {
            return ResponseEntity.ok(userService.createUserDTO(userService.getUserById(id)));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest userRequest) {
        UserEntity userEntity = userService.createUser(userRequest);

        return ResponseEntity
                .created(URI.create(String.format("/api/users/%d", userEntity.getId())))
                .body(userService.createUserDTO(userEntity));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserEntity existingUser = userService.getUserById(id);

        if (existingUser != null) {
            UserEntity updatedUser = userService.updateUser(existingUser, userRequest);
            return ResponseEntity.ok(userService.createUserDTO(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        UserEntity existingUser = userService.getUserById(id);

        if (existingUser != null) {
            userService.deleteUser(id);
            return ResponseEntity.ok(userService.createUserDTO(existingUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
