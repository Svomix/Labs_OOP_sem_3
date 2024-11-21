package Labs_OOP_sem_3.config;

import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.repositories.UserRepository;
import Labs_OOP_sem_3.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/login").permitAll().anyRequest().authenticated())
                .formLogin(login -> login.permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(PasswordEncoder encoder, UserRepository repo) {
        UserDetailsManager service = new UserService(repo);

        if (!service.userExists("user")) {
            service.createUser(UserEntity.builder()
                    .username("user")
                    .password(encoder.encode("password"))
                    .build());
        }

        return service;
    }


}
