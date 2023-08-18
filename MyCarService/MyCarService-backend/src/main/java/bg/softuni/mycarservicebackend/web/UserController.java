package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
import bg.softuni.mycarservicebackend.services.UserService;
import bg.softuni.mycarservicebackend.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDTO>> getUserVehicles(Principal principal) {
        List<VehicleDTO> allUserVehicles = vehicleService.getUserVehicles(principal);
        return ResponseEntity.ok(allUserVehicles);
    }

    @PostMapping("/vehicles")
    public ResponseEntity<VehicleDTO> addVehicle(Principal principal, @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(this.vehicleService.addVehicle(principal, vehicleDTO));
    }

    @DeleteMapping("/vehicles/{id}")
    public void deleteVehicle(Principal principal, @PathVariable Long id) {
        vehicleService.deleteVehicle(principal, id);
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicle(id));
    }
}
