package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;



@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

@Autowired
public UserRepository userrepo;

@Autowired
public PasswordEncoder passwordEncoder;

public String registeruser(@RequestBody User user) {
	// encode password before saving if it's not already encoded
	String raw = user.getPassword();
	if (raw != null && !isLikelyBCryptHash(raw)) {
		user.setPassword(passwordEncoder.encode(raw));
		logger.info("Encoded password for user='{}' before save", user.getUsername());
	} else {
		logger.info("Password for user='{}' appears already encoded or was null", user.getUsername());
	}
	userrepo.save(user);
	return "User added sucessfully";
}
public List<User> getusers(){
	return userrepo.findAll();
}

/**
 * Migrate existing plain-text passwords stored in DB to BCrypt.
 * This should be used only in development/maintenance and removed later.
 */
public int migratePlainTextPasswords() {
    List<User> users = userrepo.findAll();
    int migrated = 0;
    for (User u : users) {
        String pw = u.getPassword();
        if (pw != null && !isLikelyBCryptHash(pw)) {
            String encoded = passwordEncoder.encode(pw);
            u.setPassword(encoded);
            userrepo.save(u);
            migrated++;
            logger.info("Migrated password for user='{}'", u.getUsername());
        }
    }
    logger.info("Password migration complete. Users migrated={}", migrated);
    return migrated;
}

public boolean isPasswordEncodedForUser(String username) {
    return userrepo.findByUsername(username)
            .map(u -> u.getPassword() != null && isLikelyBCryptHash(u.getPassword()))
            .orElse(false);
}

private boolean isLikelyBCryptHash(String s) {
	if (s == null) return false;
	// BCrypt hashes start with $2a$, $2b$, $2y$ etc. Use a simple heuristic to avoid double-encoding.
	return s.startsWith("$2a$") || s.startsWith("$2b$") || s.startsWith("$2y$");
}

}