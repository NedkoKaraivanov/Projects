package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.ChangeEmailDTO;
import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.exceptions.ExistingUserException;
import bg.softuni.mycarservicebackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
            return ResponseEntity.ok(this.userService.updateProfile(principal, userDTO));
    }

    @PatchMapping("/update-email")
    public ResponseEntity<Void> updateEmail(Principal principal, @RequestBody ChangeEmailDTO changeEmailDTO) {
            this.userService.updateEmail(principal, changeEmailDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
    }

}
