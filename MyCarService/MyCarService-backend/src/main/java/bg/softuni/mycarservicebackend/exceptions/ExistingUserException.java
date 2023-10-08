package bg.softuni.mycarservicebackend.exceptions;

public class ExistingUserException extends RuntimeException {

    public ExistingUserException() {
        super("Email already exists");
    }
}
