package bg.softuni.mycarservicebackend.unit_tests.services;

import bg.softuni.mycarservicebackend.domain.dtos.BookingDTO;
import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
import bg.softuni.mycarservicebackend.domain.entities.BookingEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserRoleEntity;
import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import bg.softuni.mycarservicebackend.domain.enums.ServiceTypeEnum;
import bg.softuni.mycarservicebackend.domain.enums.UserRoleEnum;
import bg.softuni.mycarservicebackend.repositories.BookingRepository;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.VehicleRepository;
import bg.softuni.mycarservicebackend.services.BookingService;
import bg.softuni.mycarservicebackend.services.UserService;
import bg.softuni.mycarservicebackend.services.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private final String TEST_EMAIL = "user@test.com";
    private BookingService toTest;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private UserService mockUserService;

    @Mock
    private BookingRepository mockBookingRepository;

    @Mock
    private VehicleRepository mockVehicleRepository;

    @Mock
    private VehicleService mockVehicleService;

    UserRoleEntity testUserRole;

    VehicleEntity testVehicleEntity;

    UserEntity testUserEntity;

    BookingEntity firstBookingEntity;

    BookingEntity secondBookingEntity;

    @BeforeEach
    void setUp() {
        toTest = new BookingService(mockUserRepository,
                mockUserService,
                mockBookingRepository,
                mockVehicleRepository,
                mockVehicleService);

        testUserRole = UserRoleEntity.builder().role(UserRoleEnum.USER).build();

        testVehicleEntity = VehicleEntity.builder()
                .brand("BMW")
                .model("1st-Series")
                .build();

        testUserEntity = UserEntity.builder()
                .email(TEST_EMAIL)
                .password("123123")
                .roles(List.of(testUserRole, testUserRole))
                .vehicles(List.of(testVehicleEntity))
                .build();

        firstBookingEntity = BookingEntity.builder()
                .user(testUserEntity)
                .vehicle(testVehicleEntity)
                .serviceType(ServiceTypeEnum.DIAGNOSTICS)
                .isConfirmed(true)
                .build();

        secondBookingEntity = BookingEntity.builder()
                .user(testUserEntity)
                .vehicle(testVehicleEntity)
                .serviceType(ServiceTypeEnum.OIL_CHANGE)
                .build();
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

    @Test
    void testGetUserBookings() {
        Principal principal = new TestPrincipal(TEST_EMAIL);

        when(mockUserRepository.findByEmail(principal.getName()))
                .thenReturn(Optional.of(testUserEntity));
        when(mockBookingRepository.findAllByUser(testUserEntity))
                .thenReturn(List.of(firstBookingEntity));

        List<BookingDTO> testUserBookings = toTest.getUserBookings(principal);
        Assertions.assertEquals(1, testUserBookings.size());
        Assertions.assertEquals(TEST_EMAIL, firstBookingEntity.getUser().getEmail());
        Assertions.assertEquals("DIAGNOSTICS", testUserBookings.get(0).getServiceType());
    }

    @Test
    void testGetAllBookings() {
        when(mockBookingRepository.findAll())
                .thenReturn(List.of(firstBookingEntity, secondBookingEntity));

        List<BookingDTO> allBookings = toTest.getAllBookings();

        Assertions.assertEquals(2, allBookings.size());
        Assertions.assertEquals(firstBookingEntity.getServiceType().name(), allBookings.get(0).getServiceType());
        Assertions.assertTrue(allBookings.get(0).getIsConfirmed());
    }

    @Test
    void testGetBooking() {
        Long bookingId = 1L;

        when(mockBookingRepository.findById(bookingId))
                .thenReturn(Optional.of(firstBookingEntity));

        BookingDTO bookingDTO = toTest.getBooking(bookingId);

        Assertions.assertEquals(firstBookingEntity.getId(), bookingDTO.getId());
        Assertions.assertTrue(firstBookingEntity.getIsConfirmed());
        Assertions.assertEquals("DIAGNOSTICS", bookingDTO.getServiceType());
    }

    @Test
    void testCreateBooking() {
        
    }

}
