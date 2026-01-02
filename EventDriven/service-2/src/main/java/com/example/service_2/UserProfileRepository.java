package com.example.service_2;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // Find profile by the external Auth User ID
    Optional<UserProfile> findByUserId(UUID userId);

    // For deleting when a user is removed
    void deleteByUserId(UUID userId);
}