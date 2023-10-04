package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.VehicleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class VehicleControllerIT {

    private static final String TEST_EMAIL = "userEmail@test.com";

    private static final String PASSWORD = "123123";

    private static final String PHONE_NUMBER = "123123";


    private static final String BRAND_BMW = "BMW";

    private static final String BRAND_AUDI = "Audi";

    private static final String BRAND_MERCEDES = "Mercedes";

    private static final String MODEL_E_CLASS = "E-Class";

    private static final String MODEL_S_CLASS = "S-Class";

    private static final String MODEL_A6 = "A6";
    private static final String MODEL_3_Series = "3rd-Series";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeAll
    void setUp() {
        UserEntity testUser = UserEntity.builder()
                .email(TEST_EMAIL)
                .password(PASSWORD)
                .phoneNumber(PHONE_NUMBER)
                .vehicles(new ArrayList<>())
                .build();

        VehicleEntity firstVehicle = VehicleEntity.builder()
                .user(testUser)
                .brand(BRAND_BMW)
                .model(MODEL_3_Series)
                .build();

        VehicleEntity secondVehicle = VehicleEntity.builder()
                .user(testUser)
                .brand(BRAND_AUDI)
                .model(MODEL_A6)
                .build();

        testUser.getVehicles().add(firstVehicle);
        testUser.getVehicles().add(secondVehicle);
        userRepository.save(testUser);
        vehicleRepository.save(firstVehicle);
        vehicleRepository.save(secondVehicle);
    }

    @AfterAll
    void tearDown() {
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void getUserVehicles_Vehicles_Returned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/vehicles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].brand", is(BRAND_BMW)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].model", is(MODEL_3_Series)));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void getVehicle_VehicleExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/vehicles/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", is(BRAND_BMW)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", is(MODEL_3_Series)));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void getVehicle_Vehicle_Not_Exist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/vehicles/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void addVehicle_Request_Successful() throws Exception {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .brand(BRAND_MERCEDES)
                .model(MODEL_E_CLASS)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(vehicleDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", is(BRAND_MERCEDES)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", is(MODEL_E_CLASS)));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void updateVehicle_Request_Successful() throws Exception {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .id(1L)
                .brand(BRAND_MERCEDES)
                .model(MODEL_S_CLASS)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(vehicleDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand", is(BRAND_MERCEDES)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", is(MODEL_S_CLASS)));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void deleteVehicle_Vehicle_Exist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/vehicles/2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void deleteVehicle_Vehicle_Does_Not_Exist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/vehicles/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
