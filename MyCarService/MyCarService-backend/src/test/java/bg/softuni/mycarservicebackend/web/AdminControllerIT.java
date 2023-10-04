package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.BookingDTO;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("Test")
public class AdminControllerIT {

    private final String USER_EMAIL_TEST = "userEmail@test.com";

    private static final String TEST_PASSWORD = "123123";

    private static final String TEST_PHONE_NUMBER = "123123";

    private static final String BRAND_BMW = "BMW";

    private static final String BRAND_AUDI = "Audi";

    private static final String BMW_MODEL = "3rd-Series";

    private static final String AUDI_MODEL_A6 = "A6";

    private static final String SERVICE_TYPE_DIAGNOSTICS = "DIAGNOSTICS";

    private static final String SERVICE_TYPE_OIL_CHANGE = "OIL_CHANGE";

    private static final String FIRST_DESCRIPTION = "Some description";

    private static final String SECOND_DESCRIPTION = "Some test description";

    private static final Double SERVICE_PRICE = 300.00;
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
                .password(TEST_PASSWORD)
                .phoneNumber(TEST_PHONE_NUMBER)
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
                .model(AUDI_MODEL_A6)
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
    @WithMockUser(username = "adminEmail@test.com", roles = "ADMIN")
    void getAllBookings_Bookings_Returned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/all-bookings"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].user.email").value(USER_EMAIL_TEST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].vehicle.brand").value(BRAND_BMW))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].serviceType").value(SERVICE_TYPE_DIAGNOSTICS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value(FIRST_DESCRIPTION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].vehicle.brand").value(BRAND_AUDI))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].serviceType").value(SERVICE_TYPE_OIL_CHANGE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].description").value(SECOND_DESCRIPTION));
    }

    @Test
    @WithMockUser(username = "adminEmail@test.com", roles = "ADMIN")
    void getBooking_Booking_Returned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/bookings/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.email").value(USER_EMAIL_TEST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicle.brand").value(BRAND_BMW))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceType").value(SERVICE_TYPE_DIAGNOSTICS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(FIRST_DESCRIPTION));
    }

    @Test
    @WithMockUser(username = "adminEmail@test.com", roles = "ADMIN")
    void updateBooking_Booking_Updated() throws Exception {
        BookingDTO bookingDTO = BookingDTO.builder()
                .isConfirmed(true)
                .isReady(true)
                .price(SERVICE_PRICE)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(bookingDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/bookings/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.email").value(USER_EMAIL_TEST))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vehicle.brand").value(BRAND_AUDI))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isConfirmed").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isReady").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(SERVICE_PRICE));
    }

    @Test
    @WithMockUser(username = "adminEmail@test.com", roles = "ADMIN")
    void deleteBooking_Booking_Deleted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/bookings/2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
