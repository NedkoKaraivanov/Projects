package bg.softuni.mycarservicebackend.web;

import bg.softuni.mycarservicebackend.domain.dtos.BookingDTO;
import bg.softuni.mycarservicebackend.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDTO>> getUserBookings(Principal principal) {
        List<BookingDTO> userBookings = bookingService.getUserBookings(principal);
        return ResponseEntity.ok(userBookings);
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(this.bookingService.updateBooking(id, bookingDTO));
    }

    @GetMapping("bookings/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @PostMapping("/bookings")
    public ResponseEntity<BookingDTO> createBooking(Principal principal, @RequestBody BookingDTO bookingDTO) {
        BookingDTO bookingDtoCreated = this.bookingService.createBooking(principal, bookingDTO);
        return ResponseEntity.created(URI.create(String.format("/api/users/bookings/%d", bookingDtoCreated.getId())))
                .body(bookingDtoCreated);
    }

    @DeleteMapping("/bookings/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
