package bg.softuni.mycarservicebackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    HttpStatus status;
    String message;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception ex) {

        if (ex instanceof ExistingUserException) {
            status = HttpStatus.CONFLICT;
            message = ex.getMessage();
        } else if (ex instanceof AuthenticationException) {
            status = HttpStatus.UNAUTHORIZED;
            message = ex.getMessage();
        }

        return ResponseEntity.status(status).body(message);
    }
}
