package com.kdob.piq.user.infrastructure.kafka;

import com.kdob.piq.user.application.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserCreatedEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(UserCreatedEventConsumer.class);

    private final UserProfileService userProfileService;

    public UserCreatedEventConsumer(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.user.created}",
            groupId = "${app.kafka.consumer.group.id}"
    )
    public void handleUserCreated(UserCreatedEvent event) {
        logger.info("Received UserCreatedEvent: authId=[{}], email=[{}]", event.authId(), event.email());

        try {
            userProfileService.createProfileIfNotExists(event.authId(), event.email(), event.roles());
            logger.info("User profile created (or already exist) for authId=[{}]", event.authId());
        } catch (Exception e) {
            logger.error("Failed to create user profile for authId=[{}]", event.authId(), e);
            throw e; // Rethrow so Kafka retries
        }
    }
}