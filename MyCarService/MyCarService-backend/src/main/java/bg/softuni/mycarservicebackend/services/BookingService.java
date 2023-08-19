package bg.softuni.mycarservicebackend.services;

import bg.softuni.mycarservicebackend.domain.dtos.BookingDTO;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    private final VehicleRepository vehicleRepository;

    private final VehicleService vehicleService;
    public BookingDTO createBooking(Principal principal, BookingDTO bookingDTO) {
        UserEntity userEntity = userRepository.findByEmail(principal.getName()).get();
        Long vehicleId = bookingDTO.getVehicle().getId();
        VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId).get();

        BookingEntity bookingEntity = BookingEntity.builder()
                .user(userEntity)
                .vehicle(vehicleEntity)
                .bookDate(bookingDTO.getBookDate())
                .description(bookingDTO.getDescription())
                .serviceType(getServiceType(bookingDTO.getServiceType()))
                .build();

        return createBookingDTO(this.bookingRepository.save(bookingEntity));
    }

    private ServiceTypeEnum getServiceType(String serviceType) {

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
        return BookingDTO.builder()
                .id(bookingEntity.getId())
                .vehicle(vehicleDTO)
                .bookDate(bookingEntity.getBookDate())
                .finishDate(bookingEntity.getFinishDate())
                .serviceType(String.valueOf(bookingEntity.getServiceType()))
                .price(bookingEntity.getPrice())
                .description(bookingEntity.getDescription())
                .build();
    }
}
