package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
import bg.softuni.mycarservicebackend.services.UserService;
import bg.softuni.mycarservicebackend.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final VehicleService vehicleService;

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
