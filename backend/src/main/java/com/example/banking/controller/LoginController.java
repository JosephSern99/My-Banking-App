package com.example.banking.controller;

import com.example.banking.dto.LoginRequest;
import com.example.banking.dto.LoginResponse;
import com.example.banking.entity.User;
import com.example.banking.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> foundUser = userRepository.findByUsername(loginRequest.getUsername());
        if (foundUser.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), foundUser.get().getPassword())) {
            return ResponseEntity.ok().body("{\"message\": \"Login successful!\"}");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid credentials!\"}");
    }

    private String generateToken(User user) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generate a strong key!
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1 hour expiration

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}