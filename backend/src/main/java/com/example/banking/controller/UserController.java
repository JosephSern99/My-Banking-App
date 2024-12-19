package com.example.banking.controller;

import com.example.banking.dto.UserNameResponse;
import com.example.banking.entity.User;
import com.example.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

//    @CrossOrigin(origins = "http://localhost:4200")
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
//    {
//        return ResponseEntity.ok().body("Login successful");
//    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(newUser);
//        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User user = userService.findUserByEmail(email);

        if (user != null) {
            UserNameResponse response = new UserNameResponse();
            response.setName(user.getName());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build(); // Simplified 404
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}