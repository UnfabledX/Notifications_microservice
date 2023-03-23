package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PasswordResetRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PasswordResetRequestProcessor extends AbstractNotificationProcessor {
    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        notificationRepository.save(getNotification(receivedDTO));
        emailService.sendEmail(receivedDTO, null, null);
        return receivedDTO;
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        NotificationContent<PasswordResetRequest> content = receivedDTO.content();
        PasswordResetRequest resetRequest = content.getChild();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .notificationCreatedAt(LocalDateTime.now())
                .details(NotificationDetails.builder()
                        .type(resetRequest.getType().name())
                        .name(resetRequest.getOwnerName())
                        .email(resetRequest.getEmail())
                        .entityCreatedAt(resetRequest.getEntityCreatedAt())
                        .build())
                .build();
    }
}
