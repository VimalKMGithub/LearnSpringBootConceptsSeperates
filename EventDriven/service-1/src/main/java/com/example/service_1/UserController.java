package com.example.service_1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody UserRequest request) {

        // 1. Convert DTO to Entity
        User newUser = User.builder()
                .email(request.email())
                .fullName(request.fullName())
                // TODO: In real production, encrypt this with BCrypt!
                .passwordHash(request.password())
                .build();

        // 2. Save to DB
        User savedUser = userRepository.save(newUser);

        return ResponseEntity.ok("User created successfully with ID: " + savedUser.getId());
    }
}