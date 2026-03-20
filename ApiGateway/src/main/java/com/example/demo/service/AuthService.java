package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.JwtUtil;

@Service
public class AuthService {
@Autowired
public UserRepository userrepo;
@Autowired
public JwtUtil jwtutil;

@Autowired
private PasswordEncoder passwordEncoder; // Injects the BCrypt bean

public String login(String username,String password) {

    User user = userrepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if(!user.getPassword().equals(password)) {
        throw new RuntimeException("Invalid password");
    }

    // ✅ include role
    return jwtutil.generateToken(user.getUsername(), user.getRoles());
} 
//to check the password
public boolean verifyLogin(String rawPassword, String storedHash) {
    // This checks if the raw input matches the encrypted hash in the DB
    return passwordEncoder.matches(rawPassword, storedHash);
}
}
