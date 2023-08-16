package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(Principal principal) {
        String userEmail = principal.getName();
        return ResponseEntity.ok(userService.createUserDTO(userService.getUserByEmail(userEmail)));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(Principal principal, @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(this.userService.updateProfile(principal, userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).build();
        }
    }
}
