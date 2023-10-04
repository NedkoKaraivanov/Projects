package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.auth.AuthenticationRequest;
import bg.softuni.mycarservicebackend.auth.RegisterRequest;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.token.TokenRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("Test")
public class AuthenticationControllerIT {

    private static final String USER_EMAIL_TEST = "userEmail@test.com";

    private static final String NEW_USER_EMAIL = "newUser@test.com";

    private static final String PASSWORD = "123123";

    private static final String WRONG_PASSWORD = "wrongPassword";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        UserEntity testUser = UserEntity.builder()
                .email(USER_EMAIL_TEST)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        userRepository.save(testUser);
    }

    @AfterAll
    void tearDown() {
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void register_Valid_User_Request_Successful() throws Exception {
        RegisterRequest requestBody = RegisterRequest.builder()
                .email(NEW_USER_EMAIL)
                .password(PASSWORD)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles").value("1"));
    }

    @Test
    void register_With_Existing_User_Request_Invalid() throws Exception {
        RegisterRequest requestBody = RegisterRequest.builder()
                .email(USER_EMAIL_TEST)
                .password(PASSWORD)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void authenticate_With_Valid_User() throws Exception {
        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email(USER_EMAIL_TEST)
                .password(PASSWORD)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(authRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void authenticate_With_Wrong_Password_Unauthorized_Status_Returned() throws Exception {
        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .email(USER_EMAIL_TEST)
                .password(WRONG_PASSWORD)
                .build();

        String jsonRequestBody = objectMapper.writeValueAsString(authRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
