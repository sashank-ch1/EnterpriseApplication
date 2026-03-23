package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy; // Added // Edited

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter; // Added JWT filter // Edited

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs (JWT)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // use IF_REQUIRED to allow form login sessions // Edited
            .authorizeHttpRequests(auth -> auth
                    // Allow unauthenticated access to auth endpoints and the default login page // Edited
                    .requestMatchers("/auth/**", "/register/**", "/login/**", "/login", "/error", "/actuator/**").permitAll() // Edited: added "/login"
                    .requestMatchers("/products/add", "/products/delete/**").hasRole("ADMIN")
                    .requestMatchers("/products/**").hasAnyRole("USER", "ADMIN")
                    .anyRequest().authenticated()
                )
            // Use Spring Security's default login page (serve /login) so no custom UI is required // Edited
            .formLogin(Customizer.withDefaults())
            .oauth2Login(oauth2 -> oauth2.disable()) // Leave oauth2 disabled // Edited
            .oauth2Client(oauth2client -> oauth2client.disable()); // Leave oauth2 client disabled // Edited

        // Register our JWT filter before UsernamePasswordAuthenticationFilter // Edited
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
 // Inside SecurityConfig class
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // NOTE: rely on AuthenticationConfiguration to provide AuthenticationManager (avoids DaoAuthenticationProvider compatibility issues) // Edited
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}