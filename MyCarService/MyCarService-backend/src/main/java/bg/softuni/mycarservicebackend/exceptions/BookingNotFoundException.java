package bg.softuni.mycarservicebackend.exceptions;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException() {
        super("No such booking exists.");
    }
}
