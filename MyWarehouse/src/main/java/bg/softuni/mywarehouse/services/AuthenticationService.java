package bg.softuni.mywarehouse.services;

import bg.softuni.mywarehouse.auth.AuthenticationRequest;
import bg.softuni.mywarehouse.auth.AuthenticationResponse;
import bg.softuni.mywarehouse.auth.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
