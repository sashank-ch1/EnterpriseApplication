package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs (JWT)
            .authorizeHttpRequests(auth -> auth
            		// Public APIs
                    .requestMatchers("/login", "/register").permitAll()

                    // ADMIN APIs
                    .requestMatchers("/products/add", "/products/delete/**")
                    .hasRole("ADMIN")

                    // USER APIs
                    .requestMatchers("/products/**")
                    .hasAnyRole("USER", "ADMIN")

                .anyRequest().authenticated() // Everything else requires a login
            )
            // Enable Social Login (Google/GitHub/etc.)
            .oauth2Login(Customizer.withDefaults()) ;
            
            // Enable JWT Resource Server support for your local tokens
            //.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
 // Inside SecurityConfig class
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
