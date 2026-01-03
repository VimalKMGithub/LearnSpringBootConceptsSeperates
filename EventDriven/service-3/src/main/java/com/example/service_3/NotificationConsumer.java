package com.example.service_3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user-created-events", groupId = "notification-service-group")
    public void sendWelcomeEmail(String message) {
        try {
            UserEvent event = objectMapper.readValue(message, UserEvent.class);

            // Filter: We only email on "Create" (op = 'c'), not 'u' or 'd'
            if ("c".equals(event.getOp())) {
                log.info("📧 STARTING EMAIL SEQUENCE...");
                log.info("   ➡ To: {}", event.getEmail());
                log.info("   ➡ Subject: Welcome, {}!", event.getFullName());
                log.info("   ➡ Body: Thank you for joining our platform.");

                // Simulate network delay (like calling SendGrid/AWS SES)
                Thread.sleep(100);

                log.info("✅ EMAIL SENT successfully to {}", event.getEmail());
            }

        } catch (Exception e) {
            log.error("❌ Failed to process notification", e);
        }
    }
}
