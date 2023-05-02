package bg.softuni.mywarehouse.config;

import bg.softuni.mywarehouse.domain.entities.UserRoleEntity;
import bg.softuni.mywarehouse.domain.enums.UserRoleEnum;
import bg.softuni.mywarehouse.repositories.UserRepository;
import bg.softuni.mywarehouse.services.impl.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .requestMatchers("/", "/api/client/products").permitAll()
                .requestMatchers("/api/users/login", "/api/users/register").anonymous()
                .requestMatchers("/api/admin/**").hasAnyRole(String.valueOf(UserRoleEnum.ADMIN))
                .anyRequest().authenticated();
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout()
//                .logoutUrl("/api/users/logout")
//                .addLogoutHandler(logoutHandler)
//                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new ApplicationUserDetailsService(userRepository);
    }
}
