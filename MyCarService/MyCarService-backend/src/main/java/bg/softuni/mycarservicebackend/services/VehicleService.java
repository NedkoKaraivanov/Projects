package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.dtos.UserRoleDTO;
import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final UserRepository userRepository;

    private final VehicleRepository vehicleRepository;
    public VehicleDTO addVehicle(Principal principal, VehicleDTO vehicleDTO) {
        UserEntity user = userRepository.findByEmail(principal.getName()).get();

        VehicleEntity vehicle = VehicleEntity.builder()
                .user(user)
                .brand(vehicleDTO.getBrand())
                .model(vehicleDTO.getModel())
                .build();

        user.getVehicles().add(vehicle);
        vehicleRepository.save(vehicle);
        userRepository.save(user);
        return createVehicleDTO(vehicle);
    }

    public List<VehicleDTO> getUserVehicles(Principal principal) {
        UserEntity user = userRepository.findByEmail(principal.getName()).get();
        return vehicleRepository.findAllByUser(user).stream().map(this::createVehicleDTO).collect(Collectors.toList());
    }


    public void deleteVehicle(Principal principal, Long id) {
        vehicleRepository.deleteById(id);
    }

    public VehicleDTO getVehicle(Long id) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id).get();
            return createVehicleDTO(vehicleEntity);
    }

    public VehicleDTO createVehicleDTO(VehicleEntity vehicle) {
        return VehicleDTO.builder()
                .id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .build();
    }
}
