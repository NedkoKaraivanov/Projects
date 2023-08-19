package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
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
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/vehicles")
    public ResponseEntity<List<VehicleDTO>> getUserVehicles(Principal principal) {
        List<VehicleDTO> allUserVehicles = vehicleService.getUserVehicles(principal);
        return ResponseEntity.ok(allUserVehicles);
    }

    @PostMapping("/vehicles")
    public ResponseEntity<VehicleDTO> addVehicle(Principal principal, @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(this.vehicleService.addVehicle(principal, vehicleDTO));
    }

    @PutMapping("/vehicles")
    public ResponseEntity<VehicleDTO> updateVehicle(Principal principal, @RequestBody VehicleDTO vehicleDTO) {
        return ResponseEntity.ok(this.vehicleService.updateVehicle(principal, vehicleDTO));
    }

    @DeleteMapping("/vehicles/{id}")
    public void deleteVehicle(Principal principal, @PathVariable Long id) {
        vehicleService.deleteVehicle(principal ,id);
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
        VehicleDTO vehicleDTO = vehicleService.getVehicle(id);
        if (vehicleDTO != null) {
            return ResponseEntity.ok(vehicleDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
