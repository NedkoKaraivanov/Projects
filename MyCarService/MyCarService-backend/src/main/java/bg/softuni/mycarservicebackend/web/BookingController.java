package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.BookingDTO;
import bg.softuni.mycarservicebackend.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<BookingDTO> createBooking(Principal principal, @RequestBody BookingDTO bookingDTO) {
        BookingDTO bookingDtoCreated = this.bookingService.createBooking(principal, bookingDTO);
        return ResponseEntity.created(URI.create(String.format("/api/users/bookings/%d", bookingDtoCreated.getId())))
                .body(bookingDtoCreated);
    }
}
