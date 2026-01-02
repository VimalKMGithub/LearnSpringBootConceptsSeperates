package com.example.service_1;

public record UserRequest(
        String email,
        String password,
        String fullName
) {
}