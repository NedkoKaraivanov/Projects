package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.BookingDTO;
import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
import bg.softuni.mycarservicebackend.domain.entities.BookingEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import bg.softuni.mycarservicebackend.domain.enums.ServiceTypeEnum;
import bg.softuni.mycarservicebackend.repositories.BookingRepository;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@DirtiesContext
public class BookingControllerIT {

    private static final String USER_EMAIL_TEST = "userEmail@test.com";

    private static final String PASSWORD = "123123";

    private static final String PHONE_NUMBER = "123123";

    private static final String BRAND_BMW = "BMW";

    private static final String BRAND_AUDI = "Audi";

    private static final String BMW_MODEL = "3rd-Series";

    private static final String AUDI_MODEL = "A6";

    private static final String FIRST_DESCRIPTION = "Some description";

    private static final String SECOND_DESCRIPTION = "Some test description";

    private static final String UPDATED_DESCRIPTION = "Updated description";

    private static final String SERVICE_TYPE_DIAGNOSTICS = "DIAGNOSTICS";

    private static final String SERVICE_TYPE_SUSPENSION_WORK = "SUSPENSION_WORK";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        UserEntity testUser = UserEntity.builder()
                .email(USER_EMAIL_TEST)
                .password(PASSWORD)
                .phoneNumber(PHONE_NUMBER)
                .vehicles(new ArrayList<>())
                .build();

        VehicleEntity firstVehicle = VehicleEntity.builder()
                .user(testUser)
                .brand(BRAND_BMW)
                .model(BMW_MODEL)
                .build();

        VehicleEntity secondVehicle = VehicleEntity.builder()
                .user(testUser)
                .brand(BRAND_AUDI)
                .model(AUDI_MODEL)
                .build();

        BookingEntity firstTestBooking = BookingEntity.builder()
                .user(testUser)
                .vehicle(firstVehicle)
                .serviceType(ServiceTypeEnum.DIAGNOSTICS)
                .description(FIRST_DESCRIPTION)
                .build();

        BookingEntity secondTestBooking = BookingEntity.builder()
                .user(testUser)
                .vehicle(secondVehicle)
                .serviceType(ServiceTypeEnum.OIL_CHANGE)
                .description(SECOND_DESCRIPTION)
                .build();

        userRepository.save(testUser);
        vehicleRepository.save(firstVehicle);
        vehicleRepository.save(secondVehicle);
        bookingRepository.save(firstTestBooking);
        bookingRepository.save(secondTestBooking);
    }

    @AfterAll
    void tearDown() {
        bookingRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void getUserBookings_Bookings_Returned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/bookings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].user.email").value(USER_EMAIL_TEST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].vehicle.brand").value(BRAND_BMW))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].serviceType").value(SERVICE_TYPE_DIAGNOSTICS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value(FIRST_DESCRIPTION));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void getBooking_Booking_Returned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/bookings/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.email").value(USER_EMAIL_TEST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicle.brand").value(BRAND_BMW))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceType").value(SERVICE_TYPE_DIAGNOSTICS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(FIRST_DESCRIPTION));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void createBooking_Booking_Created() throws Exception {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .id(2L)
                .brand(BRAND_AUDI)
                .model(AUDI_MODEL)
                .build();

        BookingDTO bookingDTO = BookingDTO.builder()
                .vehicle(vehicleDTO)
                .serviceType("Diagnostics")
                .description(FIRST_DESCRIPTION)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(bookingDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicle.brand").value(BRAND_AUDI))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicle.model").value(AUDI_MODEL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceType").value(SERVICE_TYPE_DIAGNOSTICS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(FIRST_DESCRIPTION));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void updateBooking_Booking_Updated() throws Exception {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .id(1L)
                .brand(BRAND_BMW)
                .model(BMW_MODEL)
                .build();

        BookingDTO bookingDTO = BookingDTO.builder()
                .vehicle(vehicleDTO)
                .serviceType("Suspension Work")
                .description(UPDATED_DESCRIPTION)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(bookingDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/bookings/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicle.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicle.brand").value(BRAND_BMW))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceType").value(SERVICE_TYPE_SUSPENSION_WORK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(UPDATED_DESCRIPTION));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void deleteBooking_Booking_Exists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/bookings/2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void deleteBooking_Booking_Does_Not_Exist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/bookings/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
