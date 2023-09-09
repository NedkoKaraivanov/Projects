package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserRoleEntity;
import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.services.ApplicationUserDetailsService;
import org.junit.gen5.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.gen5.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailsServiceTest {

    private final String NOT_EXISTING_EMAIL = "someEmail@test.com";

    private final String EXISTING_EMAIL = "admin@test.com";

    private final String TEST_PASSWORD = "123123";

    private ApplicationUserDetailsService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new ApplicationUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserFound() {
        UserRoleEntity testUserRole = UserRoleEntity.builder()
                .role(UserRoleEnum.USER)
                .build();
        UserRoleEntity testAdminRole = UserRoleEntity.builder()
                .role(UserRoleEnum.ADMIN)
                .build();

        UserEntity testUserEntity = UserEntity.builder()
                .email(EXISTING_EMAIL)
                .password(TEST_PASSWORD)
                .roles(List.of(testUserRole, testAdminRole))
                .build();

        when(mockUserRepository.findByEmail(EXISTING_EMAIL)).
                thenReturn(Optional.of(testUserEntity));

        UserDetails adminDetails = toTest.loadUserByUsername(EXISTING_EMAIL);

        Assertions.assertNotNull(adminDetails);
        Assertions.assertEquals(EXISTING_EMAIL, adminDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getPassword(), adminDetails.getPassword());
        Assertions.assertEquals(2,
                adminDetails.getAuthorities().size(),
                "The authorities are supposed to be only two - ADMIN and USER.");
    }

    @Test
    void testUserNotFound() {
        assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(NOT_EXISTING_EMAIL)
        );
    }
}
