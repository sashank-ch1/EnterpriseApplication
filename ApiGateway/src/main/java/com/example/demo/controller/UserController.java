package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
@Autowired
public UserService userserv;
@Autowired
public AuthService authService;


@PostMapping("/register")
public String Addusers(@RequestBody User user) {
  userserv.registeruser(user);
  return "User Details Saved Sucessfully";
}
@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {
    // Extract values directly from the map
    String username = loginData.get("username");
    String password = loginData.get("password");

    String token = authService.login(username, password);
    return ResponseEntity.ok(token);
}

// --- Development helper endpoints (remove in production) ---
// Check if the stored password for a user is encoded
@GetMapping("/password-encoded/{username}")
public ResponseEntity<Boolean> isPasswordEncoded(@PathVariable String username) {
    boolean encoded = userserv.isPasswordEncodedForUser(username);
    return ResponseEntity.ok(encoded);
}

// Migrate existing plain-text passwords to BCrypt (dev-only). Returns number migrated.
@PostMapping("/migrate-passwords")
public ResponseEntity<Integer> migratePasswords() {
    int migrated = userserv.migratePlainTextPasswords();
    return ResponseEntity.ok(migrated);
}


}