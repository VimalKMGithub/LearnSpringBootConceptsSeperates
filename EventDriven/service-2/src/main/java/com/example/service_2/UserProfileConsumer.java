package com.example.service_2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileConsumer {

    private final UserProfileRepository userProfileRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(topics = "user-created-events", groupId = "user-service-group")
    public void consumeBatch(List<String> messages, Acknowledgment ack) {
        if (messages.isEmpty()) return;

        log.info("📦 Received batch of {} events", messages.size());
        List<UserProfile> profilesToSave = new ArrayList<>();

        for (String json : messages) {
            try {
                UserEvent event = objectMapper.readValue(json, UserEvent.class);

                // --- DELETE LOGIC ---
                if ("d".equals(event.getOp())) {
                    log.info("🗑️ Deleting profile for User ID: {}", event.getId());
                    userProfileRepository.deleteByUserId(UUID.fromString(event.getId()));
                }
                // --- CREATE / UPDATE LOGIC ---
                else {
                    UUID uid = UUID.fromString(event.getId());
                    // Find existing or create new builder
                    UserProfile profile = userProfileRepository.findByUserId(uid)
                            .orElse(UserProfile.builder().userId(uid).build());

                    profile.setEmail(event.getEmail());
                    profile.setFullName(event.getFullName());

                    // Set default avatar if it's missing
                    if (profile.getAvatarUrl() == null) {
                        profile.setAvatarUrl("https://ui-avatars.com/api/?name=" + event.getFullName());
                    }

                    profilesToSave.add(profile);
                }

            } catch (Exception e) {
                log.error("❌ Failed to parse event: {}", json, e);
            }
        }

        // Batch Save (1 DB call instead of many)
        if (!profilesToSave.isEmpty()) {
            userProfileRepository.saveAll(profilesToSave);
            log.info("✅ Synced {} profiles to DB", profilesToSave.size());
        }

        // Commit Offset
        ack.acknowledge();
    }
}