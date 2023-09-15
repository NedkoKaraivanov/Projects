package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.dtos.BookingDTO;
import bg.softuni.mycarservicebackend.domain.dtos.UserDTO;
import bg.softuni.mycarservicebackend.domain.dtos.VehicleDTO;
import bg.softuni.mycarservicebackend.domain.entities.BookingEntity;
import bg.softuni.mycarservicebackend.domain.entities.UserEntity;
import bg.softuni.mycarservicebackend.domain.entities.VehicleEntity;
import bg.softuni.mycarservicebackend.domain.enums.ServiceTypeEnum;
import bg.softuni.mycarservicebackend.repositories.BookingRepository;
import bg.softuni.mycarservicebackend.repositories.UserRepository;
import bg.softuni.mycarservicebackend.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final BookingRepository bookingRepository;

    private final VehicleRepository vehicleRepository;

    private final VehicleService vehicleService;

    public List<BookingDTO> getUserBookings(Principal principal) {
        UserEntity userEntity = this.userRepository.findByEmail(principal.getName()).get();
        List<BookingEntity> userBookings = this.bookingRepository.findAllByUser(userEntity);
        return userBookings.stream().map(this::createBookingDTO).collect(Collectors.toList());
    }

    public List<BookingDTO> getAllBookings() {
        List<BookingEntity> allBookings = this.bookingRepository.findAll();
        return allBookings.stream().map(this::createBookingDTO).collect(Collectors.toList());
    }

    public BookingDTO createBooking(Principal principal, BookingDTO bookingDTO) {
        UserEntity userEntity = userRepository.findByEmail(principal.getName()).get();
        Long vehicleId = bookingDTO.getVehicle().getId();
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId).get();

        BookingEntity bookingEntity = BookingEntity.builder()
                .user(userEntity)
                .vehicle(vehicleEntity)
                .bookingDate(bookingDTO.getBookingDate())
                .description(bookingDTO.getDescription())
                .serviceType(getServiceType(bookingDTO.getServiceType()))
                .build();

        BookingEntity savedBookingEntity = this.bookingRepository.save(bookingEntity);

        return createBookingDTO(savedBookingEntity);
    }

    public BookingDTO getBooking(Long id) {
        BookingEntity bookingEntity = bookingRepository.findById(id).get();
        VehicleEntity vehicleEntity = bookingEntity.getVehicle();
        UserEntity userEntity = bookingEntity.getUser();
        VehicleDTO vehicleDTO = this.vehicleService.createVehicleDTO(vehicleEntity);
        UserDTO userDTO = this.userService.createUserDTO(userEntity);
        return BookingDTO.builder()
                .id(bookingEntity.getId())
                .user(userDTO)
                .vehicle(vehicleDTO)
                .bookingDate(bookingEntity.getBookingDate())
                .finishDate(bookingEntity.getFinishDate())
                .isConfirmed(bookingEntity.getIsConfirmed())
                .isReady(bookingEntity.getIsReady())
                .price(bookingEntity.getPrice())
                .description(bookingEntity.getDescription())
                .serviceType(String.valueOf(bookingEntity.getServiceType()))
                .build();
    }

    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        BookingEntity bookingEntity = bookingRepository.findById(id).get();
        VehicleEntity vehicleEntity = this.vehicleRepository.findById(bookingDTO.getVehicle().getId()).get();
        bookingEntity.setVehicle(vehicleEntity);
        bookingEntity.setBookingDate(bookingDTO.getBookingDate());
        bookingEntity.setServiceType(getServiceType(bookingDTO.getServiceType()));
        bookingEntity.setDescription(bookingDTO.getDescription());
        this.bookingRepository.save(bookingEntity);
        return createBookingDTO(bookingEntity);
    }

    public BookingDTO updateAdminBooking(Long id, BookingDTO bookingDTO) {
        BookingEntity bookingEntity = bookingRepository.findById(id).get();
        bookingEntity.setPrice(bookingDTO.getPrice());
        bookingEntity.setIsReady(bookingDTO.getIsReady());
        bookingEntity.setIsConfirmed(bookingDTO.getIsConfirmed());
        this.bookingRepository.save(bookingEntity);
        return createBookingDTO(bookingEntity);
    }

    public void deleteBooking(Long id) {
        BookingEntity bookingEntity = this.bookingRepository.findById(id).orElseThrow(()-> new RuntimeException("No such booking exists"));
        Long userId = bookingEntity.getUser().getId();
        UserEntity userEntity = this.userRepository.findById(userId).get();
        userEntity.getBookings().remove(bookingEntity);
        this.userRepository.save(userEntity);
        this.bookingRepository.delete(bookingEntity);
    }

    public ServiceTypeEnum getServiceType(String serviceType) {

        return switch (serviceType) {
            case "Diagnostics" -> ServiceTypeEnum.DIAGNOSTICS;
            case "Oil Change" -> ServiceTypeEnum.OIL_CHANGE;
            case "Suspension Work" -> ServiceTypeEnum.SUSPENSION_WORK;
            case "Tire Change" -> ServiceTypeEnum.TIRE_CHANGE;
            default -> null;
        };
    }

    public BookingDTO createBookingDTO(BookingEntity bookingEntity) {
        VehicleDTO vehicleDTO = vehicleService.createVehicleDTO(bookingEntity.getVehicle());
        UserEntity userEntity = bookingEntity.getUser();
        UserDTO userDTO = this.userService.createUserDTO(userEntity);
        return BookingDTO.builder()
                .id(bookingEntity.getId())
                .user(userDTO)
                .vehicle(vehicleDTO)
                .bookingDate(bookingEntity.getBookingDate())
                .finishDate(bookingEntity.getFinishDate())
                .serviceType(String.valueOf(bookingEntity.getServiceType()))
                .price(bookingEntity.getPrice())
                .description(bookingEntity.getDescription())
                .isReady(bookingEntity.getIsReady())
                .isConfirmed(bookingEntity.getIsConfirmed())
                .build();
    }
}
