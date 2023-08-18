package bg.softuni.mycarservicebackend.init;

import bg.softuni.mycarservicebackend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TestUsersInit implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        this.userService.initTestUsers();
    }
}
