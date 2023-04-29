package bg.softuni.mywarehouse.domain.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Nullable
    private Long id;
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean isActive;

    List<String> roles;

    private String address;

    private String phoneNumber;
}
