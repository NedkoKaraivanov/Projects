package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerIT {

    private static final String TEST_EMAIL = "userEmail@test.com";

    private static final String UPDATED_EMAIL = "updatedEmail@test.com";

    private static final String EXISTING_USER_EMAIL = "existingUser@test.com";

    private static final String PHONE_NUMBER = "123123";

    private static final String UPDATED_PHONE_NUMBER = "321321";

    private static final String PASSWORD = "123123";

    private static final String NAME_PETER = "Peter";

    private static final String NAME_GEORGE = "George";

    private static final String NAME_TOMAS = "Tomas";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        createTestUsers();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void getUserProfile_requestIsMade_profileReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/profile"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(TEST_EMAIL)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is(NAME_PETER)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", is(PHONE_NUMBER)));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void updateProfile_Valid_NewEmail_Update_Successful() throws Exception {
        UserDTO updatedInfo = UserDTO.builder()
                .email(UPDATED_EMAIL)
                .firstName(NAME_GEORGE)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(updatedInfo);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(UPDATED_EMAIL)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is(NAME_GEORGE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", is(UPDATED_PHONE_NUMBER)));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void updateProfile_Invalid_NewEmail_ExceptionThrown() throws Exception {

        UserDTO updatedInfo = UserDTO.builder()
                .email(EXISTING_USER_EMAIL)
                .firstName(NAME_GEORGE)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(updatedInfo);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }


    private void createTestUsers() {
        UserEntity testUser =  UserEntity.builder()
                .email(TEST_EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .phoneNumber(PHONE_NUMBER)
                .firstName(NAME_PETER)
                .build();

        UserEntity existingUser =  UserEntity.builder()
                .email(EXISTING_USER_EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .phoneNumber(PHONE_NUMBER)
                .firstName(NAME_GEORGE)
                .build();

        this.userRepository.saveAllAndFlush(List.of(testUser, existingUser));
    }
}
