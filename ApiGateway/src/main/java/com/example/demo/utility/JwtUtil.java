package com.example.demo.utility;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;
@Component
public class JwtUtil {

    // Replaced external JJWT usage with a small signed token implementation to avoid version mismatch. // Edited
    private final byte[] secret = "mysecretkeymysecretkeymysecretkey12".getBytes(StandardCharsets.UTF_8);

    // Token format: base64Url(payload) + "." + base64Url(signature)
    // payload = username|role|expiryMillis

    public String generateToken(String username, String role) {
        long expiry = System.currentTimeMillis() + 1000L * 60 * 60; // 1 hour
        String payload = username + "|" + role + "|" + expiry;
        String b64 = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        String sig = hmacSha256(b64);
        return b64 + "." + sig;
    }

    public boolean validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 2) return false;
            String b64 = parts[0];
            String sig = parts[1];
            if (!hmacSha256(b64).equals(sig)) return false;
            String payload = new String(Base64.getUrlDecoder().decode(b64), StandardCharsets.UTF_8);
            String[] fields = payload.split("\\|");
            long expiry = Long.parseLong(fields[2]);
            return System.currentTimeMillis() < expiry;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 2) return null;
            String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            String[] fields = payload.split("\\|");
            return fields[0];
        } catch (Exception e) {
            return null;
        }
    }

    public String extractRole(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 2) return null;
            String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            String[] fields = payload.split("\\|");
            return fields[1];
        } catch (Exception e) {
            return null;
        }
    }

    private String hmacSha256(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(secret, "HmacSHA256");
            mac.init(keySpec);
            byte[] sig = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(sig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}