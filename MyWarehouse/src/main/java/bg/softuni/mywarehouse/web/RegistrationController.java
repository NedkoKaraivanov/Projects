package bg.softuni.mywarehouse.web;

import bg.softuni.mywarehouse.domain.dtos.UserRegistrationDTO;
import bg.softuni.mywarehouse.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users", consumes = "application/json", produces = "application/json")
public class RegistrationController {

    private static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder sb = new StringBuilder();
            for(FieldError error : errors) {
                sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        userService.registerUser(userRegistrationDTO);

        return new ResponseEntity<String>("User registered successfully", HttpStatus.OK);
    }
}
