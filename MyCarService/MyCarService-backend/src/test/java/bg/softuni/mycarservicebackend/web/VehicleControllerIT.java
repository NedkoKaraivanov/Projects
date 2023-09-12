package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.VehicleRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class VehicleControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeAll
    void setUp() {
        UserEntity testUser = UserEntity.builder()
                .email("userEmail@test.com")
                .password("123123")
                .phoneNumber("123123")
                .vehicles(new ArrayList<>())
                .build();

        VehicleEntity firstVehicle = VehicleEntity.builder()
                .user(testUser)
                .brand("BMW")
                .model("3rd-Series")
                .build();

        VehicleEntity secondVehicle = VehicleEntity.builder()
                .user(testUser)
                .brand("Audi")
                .model("A6")
                .build();

        testUser.getVehicles().add(firstVehicle);
        testUser.getVehicles().add(secondVehicle);
        userRepository.save(testUser);
        vehicleRepository.save(firstVehicle);
        vehicleRepository.save(secondVehicle);
    }

    @AfterAll
    void tearDown() {
        userRepository.deleteAll();
        vehicleRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void getUserVehicles_Request_Successful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/vehicles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].brand", is("BMW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].model", is("3rd-Series")));
    }


}
