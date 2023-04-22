package bg.softuni.mywarehouse.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
