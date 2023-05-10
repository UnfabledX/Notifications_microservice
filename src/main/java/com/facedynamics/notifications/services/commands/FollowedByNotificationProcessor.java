package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.dto.FollowedBy;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.repository.NotificationRepository;
import com.facedynamics.notifications.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FollowedByNotificationProcessor implements NotificationProcessor {

    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        notificationRepository.save(getNotification(receivedDTO));
        emailService.sendEmail(receivedDTO, null, null);
        return receivedDTO;
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        if (receivedDTO.content() instanceof FollowedBy followedBy) {
            return Notification.builder()
                    .ownerId(receivedDTO.recipientId())
                    .createdById(receivedDTO.createdById())
                    .notificationCreatedAt(LocalDateTime.now())
                    .details(NotificationDetails.builder()
                            .type(followedBy.getType().name())
                            .entityCreatedAt(followedBy.getEntityCreatedAt())
                            .build())
                    .build();
        } else {
            throw new IllegalArgumentException("Wrong type of the notification!");
        }
    }
}
