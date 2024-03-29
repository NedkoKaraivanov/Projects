package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.entities.UserRoleEntity;
import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import bg.softuni.mycarservicebackend.repositories.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    private static final Long COUNT_ROLES = 0L;

    @Mock
    private UserRoleRepository mockUserRoleRepository;

    @InjectMocks
    private UserRoleService toTest;

    @Test
    void testInitRoles() {
        List<UserRoleEntity> expectedRoles = Arrays.stream(UserRoleEnum.values())
                .map(roleEnum -> UserRoleEntity.builder().role(roleEnum).build())
                .collect(Collectors.toList());

        when(mockUserRoleRepository.count()).thenReturn(COUNT_ROLES);

        when(mockUserRoleRepository.saveAll(expectedRoles))
                .thenReturn(expectedRoles);

        toTest.initRoles();

        verify(mockUserRoleRepository).saveAll(expectedRoles);
    }
}
