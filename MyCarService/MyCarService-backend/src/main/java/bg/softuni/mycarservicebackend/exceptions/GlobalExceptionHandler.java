package bg.softuni.mycarservicebackend.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    HttpStatus status;
    String message;

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception ex) {

        if (ex instanceof ExistingUserException) {
            status = HttpStatus.CONFLICT;
            message = ex.getMessage();
            logger.error("An error occurred: {}", ex.getMessage(), ex);
        } else if (ex instanceof AuthenticationException) {
            status = HttpStatus.UNAUTHORIZED;
            message = ex.getMessage();
            logger.error("An error occurred: {}", ex.getMessage(), ex);
        } else if (ex instanceof BookingNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = ex.getMessage();
            logger.error("An error occurred: {}", ex.getMessage(), ex);
        }

        return ResponseEntity.status(status).body(message);
    }
}
