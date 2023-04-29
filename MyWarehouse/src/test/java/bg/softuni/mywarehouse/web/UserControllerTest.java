package bg.softuni.mywarehouse.web;

import bg.softuni.mywarehouse.domain.entities.UserEntity;
import bg.softuni.mywarehouse.domain.entities.UserRoleEntity;
import bg.softuni.mywarehouse.domain.enums.UserRoleEnum;
import bg.softuni.mywarehouse.domain.request.UserRequest;
import bg.softuni.mywarehouse.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllUsers_requestIsMade_AllUsersReturned() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(createUser("test1@abv", "Test1"),
                createUser("test2@abv", "Test2")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].email", is("test1@abv")))
                .andExpect(jsonPath("$.[0].firstName", is("Test1")))
                .andExpect(jsonPath("$.[1].email", is("test2@abv")))
                .andExpect(jsonPath("$.[1].firstName", is("Test2")));
    }

    @Test
    public void getUser_UserExists_UserReturned() throws Exception {
        when(userService.getUserById(1L)).thenReturn(createUser("test1@abv", "Test1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test1@abv")))
                .andExpect(jsonPath("$.firstName", is("Test1")));
    }

    @Test
    public void getUser_userDoesNotExist_notFoundStatusIsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createUser_newUserCreated() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test1@abv");
        userRequest.setFirstName("Test1");
        UserEntity user = createUser("test1@abv", "Test1");
        when(userService.createUser(userRequest)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .content(objectMapper.writeValueAsString(userRequest))
                        .contentType("application/json")
                        .accept("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("test1@abv")))
                .andExpect(jsonPath("$.firstName", is("Test1")));
    }

    @Test
    public void updateUser_existingUser_userIsUpdated() throws Exception {
        UserEntity existingUser = createUser("test1@abv", "Test1");
        existingUser.setId(1L);
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test1Updated@abv");
        userRequest.setFirstName("Test1Updated");
        when(userService.getUserById(1L)).thenReturn(existingUser);
        when(userService.updateUser(existingUser, userRequest)).then(invocation -> {
            existingUser.setEmail("test1Updated@abv");
            existingUser.setFirstName("Test1Updated");
            return existingUser;
        });


        mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/1")
                        .content(objectMapper.writeValueAsString(userRequest))
                .contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test1Updated@abv")))
                .andExpect(jsonPath("$.firstName", is("Test1Updated")));
    }

    @Test
    public void deleteUser_existingUser_userIsDeleted() throws Exception {
        UserEntity user = createUser("test1@abv", "Test1");
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(user);
        when(userService.deleteUser(1L)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test1@abv")))
                .andExpect(jsonPath("$.firstName", is("Test1")));
    }

    @Test
    public void deleteUser_nonExistent_userIsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(status().isNotFound());
    }

    private UserEntity createUser(String email, String firstName) {
        UserEntity user = new UserEntity();
        UserRoleEntity role = new UserRoleEntity(UserRoleEnum.USER);
        user.setRoles(List.of(role));
        user.setEmail(email);
        user.setFirstName(firstName);
        return user;
    }
}
