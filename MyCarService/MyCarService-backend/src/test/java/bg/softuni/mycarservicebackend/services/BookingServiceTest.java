package bg.softuni.mycarservicebackend.services;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private static final String TEST_EMAIL = "user@test.com";

    private static final String TEST_PASSWORD = "123123";

    private static final String ENUM_DIAGNOSTICS = "DIAGNOSTICS";

    private static final String BRAND_BMW = "BMW";

    private static final String BMW_MODEL = "1st-Series";

    private static final String BRAND_AUDI = "Audi";

    private static final String AUDI_MODEL = "A4";

    private static final String TEST_DESCRIPTION = "Some description";

    private static final Double TEST_PRICE = 500.00;

    private static final Long BOOKING_ID = 1L;

    private static final Long VEHICLE_ID = 1L;

    private static final Long USER_ID = 1L;

    private static final String TEST_SERVICE_TYPE_DIAGNOSTICS = "Diagnostics";
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

    @Captor
    private ArgumentCaptor<BookingEntity> bookingEntityArgumentCaptor;

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
                .brand(BRAND_BMW)
                .model(BMW_MODEL)
                .build();

        testUserEntity = UserEntity.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
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
        Assertions.assertEquals(ENUM_DIAGNOSTICS, testUserBookings.get(0).getServiceType());
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
        when(mockBookingRepository.findById(BOOKING_ID))
                .thenReturn(Optional.of(firstBookingEntity));

        BookingDTO bookingDTO = toTest.getBooking(BOOKING_ID);

        Assertions.assertEquals(firstBookingEntity.getId(), bookingDTO.getId());
        Assertions.assertTrue(firstBookingEntity.getIsConfirmed());
        Assertions.assertEquals(ENUM_DIAGNOSTICS, bookingDTO.getServiceType());
    }

    @Test
    void testCreateBooking() {
        Principal principal = new TestPrincipal(TEST_EMAIL);

        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .id(VEHICLE_ID)
                .brand(BRAND_BMW)
                .model(BMW_MODEL)
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(USER_ID)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .vehicles(List.of(vehicleDTO))
                .build();

        BookingDTO bookingDTO = BookingDTO.builder()
                .user(userDTO)
                .vehicle(vehicleDTO)
                .isConfirmed(true)
                .serviceType(ENUM_DIAGNOSTICS).build();

        BookingEntity bookingToBeSaved = BookingEntity
                .builder()
                .user(testUserEntity)
                .vehicle(testVehicleEntity)
                .serviceType(ServiceTypeEnum.DIAGNOSTICS)
                .isConfirmed(true)
                .build();

        when(mockUserRepository.findByEmail(principal.getName()))
                .thenReturn(Optional.of(testUserEntity));

        when(mockVehicleRepository.findById(VEHICLE_ID))
                .thenReturn(Optional.of(testVehicleEntity));

        when(mockVehicleService.createVehicleDTO(testVehicleEntity))
                .thenReturn(vehicleDTO);

        when(mockBookingRepository.save(any()))
                .thenReturn(bookingToBeSaved);

        toTest.createBooking(principal, bookingDTO);

        Mockito.verify(mockBookingRepository).save(bookingEntityArgumentCaptor.capture());

        BookingEntity actualSavedBooking = bookingEntityArgumentCaptor.getValue();

        Assertions.assertNotNull(actualSavedBooking);
        Assertions.assertEquals(bookingToBeSaved.getUser().getEmail(), actualSavedBooking.getUser().getEmail());
        Assertions.assertEquals(bookingToBeSaved.getVehicle().getBrand(), actualSavedBooking.getVehicle().getBrand());
    }

    @Test
    void testUpdateBooking() {

        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .id(VEHICLE_ID)
                .brand(BRAND_AUDI)
                .model(AUDI_MODEL)
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(USER_ID)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .vehicles(List.of(vehicleDTO))
                .build();

        BookingDTO bookingDTO = BookingDTO.builder()
                .user(userDTO)
                .vehicle(vehicleDTO)
                .serviceType("Oil Change")
                .description(TEST_DESCRIPTION)
                .build();

        VehicleEntity newTestVehicle = VehicleEntity.builder()
                .brand(BRAND_AUDI)
                .model(AUDI_MODEL)
                .build();

        when(mockBookingRepository.findById(BOOKING_ID))
                .thenReturn(Optional.of(firstBookingEntity));

        when(mockVehicleRepository.findById(bookingDTO.getVehicle().getId()))
                .thenReturn(Optional.of(newTestVehicle));

        firstBookingEntity.setServiceType(ServiceTypeEnum.OIL_CHANGE);
        firstBookingEntity.setDescription(bookingDTO.getDescription());
        firstBookingEntity.setVehicle(newTestVehicle);

        toTest.updateBooking(BOOKING_ID, bookingDTO);

        Mockito.verify(mockBookingRepository).save(bookingEntityArgumentCaptor.capture());

        BookingEntity actualSavedBooking = bookingEntityArgumentCaptor.getValue();

        Assertions.assertEquals(bookingDTO.getDescription(), actualSavedBooking.getDescription());
        Assertions.assertEquals(ServiceTypeEnum.OIL_CHANGE, actualSavedBooking.getServiceType());
        Assertions.assertEquals(bookingDTO.getVehicle().getBrand(), actualSavedBooking.getVehicle().getBrand());
        Assertions.assertEquals(bookingDTO.getVehicle().getModel(), actualSavedBooking.getVehicle().getModel());
    }

    @Test
    void testUpdateAdminBooking() {

        BookingDTO bookingDTO = BookingDTO.builder()
                .isReady(true)
                .isConfirmed(true)
                .price(TEST_PRICE)
                .build();

        when(mockBookingRepository.findById(BOOKING_ID))
                .thenReturn(Optional.of(firstBookingEntity));

        firstBookingEntity.setIsReady(bookingDTO.getIsReady());
        firstBookingEntity.setIsConfirmed(bookingDTO.getIsConfirmed());
        firstBookingEntity.setPrice(bookingDTO.getPrice());

        toTest.updateAdminBooking(BOOKING_ID, bookingDTO);

        Mockito.verify(mockBookingRepository).save(bookingEntityArgumentCaptor.capture());

        BookingEntity actualSavedBooking = bookingEntityArgumentCaptor.getValue();

        Assertions.assertTrue(actualSavedBooking.getIsConfirmed());
        Assertions.assertTrue(actualSavedBooking.getIsReady());
        Assertions.assertEquals(firstBookingEntity.getPrice(), actualSavedBooking.getPrice());
    }

    @Test
    void testDeleteBooking() {
        UserEntity mockUserEntity = mock(UserEntity.class);

        when(mockBookingRepository.findById(BOOKING_ID))
                .thenReturn(Optional.of(firstBookingEntity));

        Long userId = firstBookingEntity.getUser().getId();

        when(mockUserRepository.findById(userId))
                .thenReturn(Optional.of(mockUserEntity));

        List<BookingEntity> bookingsList = new ArrayList<>();
        bookingsList.add(firstBookingEntity);

        when(mockUserEntity.getBookings()).thenReturn(bookingsList);

        toTest.deleteBooking(BOOKING_ID);

        Mockito.verify(mockUserRepository).save(any());
        Mockito.verify(mockBookingRepository).delete(any());
    }

    @Test
    void testGetServiceType() {

        ServiceTypeEnum serviceTypeEnum = toTest.getServiceType(TEST_SERVICE_TYPE_DIAGNOSTICS);

        Assertions.assertNotNull(serviceTypeEnum);
        Assertions.assertEquals(ServiceTypeEnum.DIAGNOSTICS, serviceTypeEnum);
    }
}
