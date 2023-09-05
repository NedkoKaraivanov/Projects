package bg.softuni.mycarservicebackend.unit_tests.services;

import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserRoleEntity;
import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.UserRoleRepository;
import bg.softuni.mycarservicebackend.services.UserService;
import org.junit.gen5.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final String NOT_EXISTING_EMAIL = "notExisting@test.com";

    private final String EXISTING_EMAIL = "admin@test.com";

    private final String EXISTING_RANDOM_EMAIL = "existingRandomEmail@test.com";

    private final String TEST_PASSWORD = "123123";

    private UserService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @Mock
    private ModelMapper mockModelMapper;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void setUp() {
        toTest = new UserService(mockUserRepository, mockUserRoleRepository, mockModelMapper, mockPasswordEncoder);
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

    @Test()
    void testGetUserByEmailUserFound() {

        UserRoleEntity testAdminRole = UserRoleEntity.builder().role(UserRoleEnum.ADMIN).build();
        UserRoleEntity testUserRole = UserRoleEntity.builder().role(UserRoleEnum.USER).build();


        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password(TEST_PASSWORD)
                .firstName("adminFirstName")
                .lastName("adminLastName")
                .roles(List.of(testAdminRole, testUserRole)).build();

        when(mockUserRepository.findByEmail(EXISTING_EMAIL))
                .thenReturn(Optional.of(testUserEntity));

        UserEntity userEntity = toTest.getUserByEmail(EXISTING_EMAIL);

        Assertions.assertNotNull(userEntity);
        Assertions.assertEquals(EXISTING_EMAIL, userEntity.getEmail());
        Assertions.assertEquals(testUserEntity.getPassword(), userEntity.getPassword());
        Assertions.assertEquals(testUserEntity.getFirstName(), userEntity.getFirstName());
        Assertions.assertEquals(testUserEntity.getLastName(), userEntity.getLastName());

        Assertions.assertEquals(2, userEntity.getRoles().size());
        Assertions.assertEquals(userEntity.getRoles().get(0).getRole(), UserRoleEnum.ADMIN);
        Assertions.assertEquals(userEntity.getRoles().get(1).getRole(), UserRoleEnum.USER);
    }

    @Test
    void testGetUserByEmailUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            toTest.getUserByEmail(NOT_EXISTING_EMAIL);
        });
    }

    @Test
    void testUpdateProfileValidEmail() {
        Principal principal = new TestPrincipal(EXISTING_EMAIL);
        UserRoleEntity testAdminRole = UserRoleEntity.builder().role(UserRoleEnum.ADMIN).build();

        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password(TEST_PASSWORD)
                .roles(List.of(testAdminRole))
                .build();

        UserDTO newUserInformation = UserDTO.builder()
                .email("newAdminEmail")
                .password(TEST_PASSWORD).build();

        when(mockUserRepository.findByEmail(EXISTING_EMAIL))
                .thenReturn(Optional.of(testUserEntity));

        UserDTO resultDTO = toTest.updateProfile(principal, newUserInformation);

        Assertions.assertNotEquals(testUserEntity.getEmail(), newUserInformation.getEmail());
        Mockito.verify(mockUserRepository).save(any());
    }

    @Test
    void testUpdateProfileInvalidEmail() {
        Principal principal = new TestPrincipal(EXISTING_EMAIL);
        UserRoleEntity testAdminRole = UserRoleEntity.builder().role(UserRoleEnum.ADMIN).build();

        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password(TEST_PASSWORD)
                .roles(List.of(testAdminRole))
                .build();

        UserEntity existingRandomUser = UserEntity.builder()
                .email(EXISTING_RANDOM_EMAIL)
                .password(TEST_PASSWORD)
                .roles(List.of(new UserRoleEntity(UserRoleEnum.USER))).build();

        when(mockUserRepository.findByEmail(principal.getName()))
                .thenReturn(Optional.of(testUserEntity));

        when(mockUserRepository.findByEmail(EXISTING_RANDOM_EMAIL))
                .thenReturn(Optional.of(existingRandomUser));

        UserDTO newUserInformation = UserDTO.builder()
                .email(EXISTING_RANDOM_EMAIL)
                .build();

        Assertions.assertThrows(RuntimeException.class, () -> {
            toTest.updateProfile(principal, newUserInformation);
        });
    }

    @Test
    void testInitTestUsers_SaveInvoked() {
        UserRoleEntity testAdminRole = UserRoleEntity.builder().role(UserRoleEnum.ADMIN).build();
        UserRoleEntity testUserRole = UserRoleEntity.builder().role(UserRoleEnum.USER).build();

        when(mockUserRoleRepository.findByRole(UserRoleEnum.ADMIN))
                .thenReturn(testAdminRole);
        when(mockUserRoleRepository.findByRole(UserRoleEnum.USER))
                .thenReturn(testUserRole);

        UserEntity testAdminEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .roles(List.of(testAdminRole))
                .build();

        UserEntity testUserEntity = UserEntity.builder()
                .email("user@test.com")
                .roles(List.of(testUserRole))
                .build();

        toTest.initTestUsers();

        Mockito.verify(mockUserRepository).save(testAdminEntity);
        Mockito.verify(mockUserRepository).save(testUserEntity);
    }
}
