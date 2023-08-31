package bg.softuni.mycarservicebackend.unit_tests.services;

import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserRoleEntity;
import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.VehicleRepository;
import bg.softuni.mycarservicebackend.services.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private final String EXISTING_EMAIL = "user@test.com";

    private VehicleService toTest;
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private VehicleRepository mockVehicleRepository;

    @BeforeEach
    void setUp() {
        toTest = new VehicleService(mockUserRepository, mockVehicleRepository);
    }

    public static class TestPrincipal implements Principal {
        private final String email;

        public TestPrincipal(String email) {
            this.email = email;
        }

        @Override
        public String getName() {
            return email;
        }
    }

    @Test
    void testGetUserVehicles() {
        Principal principal = new TestPrincipal(EXISTING_EMAIL);
        UserRoleEntity testAdminRole = UserRoleEntity.builder().role(UserRoleEnum.ADMIN).build();

        VehicleEntity testVehicleEntity = VehicleEntity.builder()
                .brand("BMW")
                .model("1st-Series")
                .build();

        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password("123123")
                .roles(List.of(testAdminRole))
                .vehicles(List.of(testVehicleEntity))
                .build();

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
                .brand("BMW")
                .model("1st-Series")
                .build();

        when(mockVehicleRepository.findById(1L))
                .thenReturn(Optional.of(testVehicleEntity));

        VehicleDTO vehicleDTO = toTest.getVehicle(1L);

        Assertions.assertEquals(vehicleDTO.getBrand(), testVehicleEntity.getBrand());
        Assertions.assertEquals(vehicleDTO.getModel(), testVehicleEntity.getModel());
    }

    @Test
    void testAddVehicle() {
        Principal principal = new TestPrincipal(EXISTING_EMAIL);
        UserRoleEntity testUserRole = UserRoleEntity.builder().role(UserRoleEnum.USER).build();

        VehicleDTO testVehicleDTO = VehicleDTO.builder()
                .brand("BMW")
                .model("1st-Series")
                .build();

        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password("123123")
                .roles(List.of(testUserRole))
                .vehicles(new ArrayList<>())
                .build();

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
                .id(1L)
                .brand("BMW")
                .model("1st-Series")
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
        Long vehicleId = 1L;
        UserRoleEntity testUserRole = UserRoleEntity.builder().role(UserRoleEnum.USER).build();

        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password("123123")
                .roles(List.of(testUserRole))
                .vehicles(new ArrayList<>())
                .build();

        VehicleEntity testVehicleEntity = VehicleEntity.builder()
                .user(testUserEntity)
                .brand("BMW")
                .model("1st-Series")
                .build();

        when(mockVehicleRepository.findById(vehicleId))
                .thenReturn(Optional.of(testVehicleEntity));

        toTest.deleteVehicle(vehicleId);

        Mockito.verify(mockUserRepository).save(any());
        Mockito.verify(mockVehicleRepository).delete(any());
    }
}
