package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerIT {

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

    private final String TEST_EMAIL = "userEmail@test.com";

    private final String NEW_EMAIL = "updatedEmail@test.com";

    private final String TEST_PHONE_NUMBER = "123123";

    @BeforeAll
    void setUp() {
        UserEntity testUser = createTestUser();
        userRepository.save(testUser);
    }

    @AfterAll
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void getUserProfile_requestIsMade_profileReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/profile"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(TEST_EMAIL)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("Peter")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", is("123123")));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void updateProfile_Valid_NewEmail_Update_Successful() throws Exception {
        UserDTO updatedInfo = UserDTO.builder()
                .email(NEW_EMAIL)
                .firstName("George")
                .phoneNumber("321321")
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(updatedInfo);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(NEW_EMAIL)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("George")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", is("321321")));
    }

    @Test
    @WithMockUser(username = "userEmail@test.com", roles = "USER")
    void updateProfile_Invalid_NewEmail_ExceptionThrown() throws Exception {
        UserEntity existingUser = UserEntity.builder()
                .email("anotherUser@test.com")
                .password(passwordEncoder.encode("123123"))
                .phoneNumber(TEST_PHONE_NUMBER)
                .firstName("Tomas")
                .build();

        userRepository.save(existingUser);

        UserDTO updatedInfo = UserDTO.builder()
                .email("anotherUser@test.com")
                .firstName("George")
                .phoneNumber("321321")
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(updatedInfo);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }


    private UserEntity createTestUser() {
        return UserEntity.builder()
                .email(TEST_EMAIL)
                .password(passwordEncoder.encode("123123"))
                .phoneNumber(TEST_PHONE_NUMBER)
                .firstName("Peter")
                .build();
    }
}
