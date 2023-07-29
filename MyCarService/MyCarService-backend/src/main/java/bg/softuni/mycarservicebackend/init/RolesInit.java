package bg.softuni.mycarservicebackend.init;

import bg.softuni.mycarservicebackend.services.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RolesInit implements CommandLineRunner {

    private final UserRoleService userRoleService;

    @Override
    public void run(String... args) throws Exception {
        userRoleService.initRoles();
    }
}
