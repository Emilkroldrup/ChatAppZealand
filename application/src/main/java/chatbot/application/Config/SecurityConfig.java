package chatbot.application.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Allow static resources
                .requestMatchers("/login").permitAll()
                .requestMatchers("/h2-console/**").hasRole("ADMIN") // Allow access to the H2 console
                .requestMatchers("/users/create").permitAll()
                .anyRequest().authenticated() // All other requests must be authenticated
            )
            .formLogin((form) -> form
                .loginPage("/login").permitAll() // Custom login page
            )
            .logout((logout) -> logout.permitAll())
            .csrf((csrf) -> csrf.disable()) // Disable CSRF for H2 console access
            .headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable())); // Allow H2 console to be displayed in a frame

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build());
        manager.createUser(User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
