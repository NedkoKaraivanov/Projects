package bg.softuni.mycarservicebackend.exceptions;

public class ExistingUserException extends RuntimeException {

    public ExistingUserException() {
        super("User with this email already exists.");
    }
}
