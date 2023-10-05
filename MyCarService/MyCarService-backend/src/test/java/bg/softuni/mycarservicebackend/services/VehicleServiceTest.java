package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserRoleEntity;
import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    public static final String PASSWORD = "123123";
    private static final String EXISTING_EMAIL = "user@test.com";

    private static final String BRAND_BMW = "BMW";

    private static final String BMW_MODEL = "1st-Series";

    private static final Long VEHICLE_ID = 1L;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private VehicleRepository mockVehicleRepository;

    @Mock
    private Principal principal;

    @InjectMocks
    private VehicleService toTest;

    @Test
    void testGetUserVehicles() {
        UserRoleEntity testAdminRole = UserRoleEntity.builder().role(UserRoleEnum.ADMIN).build();

        VehicleEntity testVehicleEntity = VehicleEntity.builder()
                .brand(BRAND_BMW)
                .model(BMW_MODEL)
                .build();

        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password(PASSWORD)
                .roles(List.of(testAdminRole))
                .vehicles(List.of(testVehicleEntity))
                .build();

        when(principal.getName()).thenReturn(EXISTING_EMAIL);

        when(mockUserRepository.findByEmail(principal.getName()))
                .thenReturn(Optional.of(testUserEntity));

        when(mockVehicleRepository.findAllByUser(testUserEntity))
                .thenReturn(List.of(testVehicleEntity));

        toTest.getUserVehicles(principal);

        Assertions.assertEquals(testUserEntity.getVehicles().get(0), testVehicleEntity);
    }

    @Test
    void testGetVehicle_ExistingVehicle() {
        VehicleEntity testVehicleEntity = VehicleEntity.builder()
                .brand(BRAND_BMW)
                .model(BMW_MODEL)
                .build();

        when(mockVehicleRepository.findById(VEHICLE_ID))
                .thenReturn(Optional.of(testVehicleEntity));

        VehicleDTO vehicleDTO = toTest.getVehicle(VEHICLE_ID);

        Assertions.assertEquals(vehicleDTO.getBrand(), testVehicleEntity.getBrand());
        Assertions.assertEquals(vehicleDTO.getModel(), testVehicleEntity.getModel());
    }

    @Test
    void testAddVehicle() {
        UserRoleEntity testUserRole = UserRoleEntity.builder().role(UserRoleEnum.USER).build();

        VehicleDTO testVehicleDTO = VehicleDTO.builder()
                .brand(BRAND_BMW)
                .model(BMW_MODEL)
                .build();

        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password(PASSWORD)
                .roles(List.of(testUserRole))
                .vehicles(new ArrayList<>())
                .build();

        when(principal.getName()).thenReturn(EXISTING_EMAIL);

        when(mockUserRepository.findByEmail(principal.getName()))
                .thenReturn(Optional.of(testUserEntity));

        VehicleDTO returnVehicleDTO = toTest.addVehicle(principal, testVehicleDTO);

        Mockito.verify(mockVehicleRepository).save(any());
        Mockito.verify(mockUserRepository).save(any());
        Assertions.assertEquals(returnVehicleDTO.getBrand(), testVehicleDTO.getBrand());
        Assertions.assertEquals(returnVehicleDTO.getModel(), testVehicleDTO.getModel());
    }

    @Test
    void testUpdateVehicle() {
        VehicleDTO testVehicleDTO = VehicleDTO.builder()
                .id(VEHICLE_ID)
                .brand(BRAND_BMW)
                .model(BMW_MODEL)
                .build();

        VehicleEntity testVehicleEntity = VehicleEntity.builder()
                .brand(testVehicleDTO.getBrand())
                .model(testVehicleDTO.getModel())
                .build();

        when(mockVehicleRepository.findById(testVehicleDTO.getId()))
                .thenReturn(Optional.of(testVehicleEntity));

        toTest.updateVehicle(testVehicleDTO);

        Mockito.verify(mockVehicleRepository).save(any());
    }

    @Test
    void testDeleteVehicle() {
        UserRoleEntity testUserRole = UserRoleEntity.builder().role(UserRoleEnum.USER).build();

        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password(PASSWORD)
                .roles(List.of(testUserRole))
                .vehicles(new ArrayList<>())
                .build();

        VehicleEntity testVehicleEntity = VehicleEntity.builder()
                .user(testUserEntity)
                .brand(BRAND_BMW)
                .model(BMW_MODEL)
                .build();

        when(mockVehicleRepository.findById(VEHICLE_ID))
                .thenReturn(Optional.of(testVehicleEntity));

        toTest.deleteVehicle(VEHICLE_ID);

        Mockito.verify(mockUserRepository).save(any());
        Mockito.verify(mockVehicleRepository).delete(any());
    }
}
