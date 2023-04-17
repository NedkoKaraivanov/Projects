package bg.softuni.mywarehouse.init;

import bg.softuni.mywarehouse.services.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RolesInit implements CommandLineRunner {

    private final UserRoleService userRoleService;

    @Override
    public void run(String... args) throws Exception {
        userRoleService.initRoles();
    }
}
