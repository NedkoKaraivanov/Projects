package bg.softuni.mywarehouse;

import bg.softuni.mywarehouse.domain.entities.UserEntity;
import bg.softuni.mywarehouse.domain.request.UserRequest;
import bg.softuni.mywarehouse.repositories.UserRepository;
import bg.softuni.mywarehouse.services.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UsersIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    void setUp() {
        List<UserEntity> testUsers = createTestUsers();
        userRepository.saveAll(testUsers);

    }

    @AfterAll
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void getUserById_userIsFound_userReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Test1FirstName")))
                .andExpect(jsonPath("$.email", is("Test1@abv")));
    }

    @Test
    public void getUserById_userNotFound_statusReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllUsers_usersFound_usersReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName", is("Test1FirstName")))
                .andExpect(jsonPath("$.[0].email", is("Test1@abv")))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[1].firstName", is("Test2FirstName")))
                .andExpect(jsonPath("$.[1].email", is("Test2@abv")))
                .andExpect(jsonPath("$.[1].id", is(2)));
    }

    @Test
    public void deleteUser_attemptToDeleteUserTwice_userIsDeletedAndNotFoundIsReturnedTheSecondTime() throws Exception {
        UserRequest user = new UserRequest();
        user.setEmail("UserToBeDeleted@abv");
        user.setFirstName("userToBeDeleted");
        user.setPassword("toBeDeleted");
        userService.createUser(user);
        UserEntity existingUser = userService.getUserByEmail("UserToBeDeleted@abv");
        Long id = existingUser.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("userToBeDeleted")));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/users/" + id))
                .andExpect(status().isNotFound());
    }

    private List<UserEntity> createTestUsers() {
        UserEntity user1 = new UserEntity();
        user1.setFirstName("Test1FirstName");
        user1.setEmail("Test1@abv");
        user1.setPassword(passwordEncoder.encode("test1Password"));
        UserEntity user2 = new UserEntity();
        user2.setFirstName("Test2FirstName");
        user2.setEmail("Test2@abv");
        user2.setPassword(passwordEncoder.encode("test2Password"));
        return List.of(user1, user2);
    }
}
