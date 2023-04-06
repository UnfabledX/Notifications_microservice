package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.UserRegistered;

import java.time.LocalDateTime;

public class UserRegisteredNotificationProcessor extends AbstractNotificationProcessor {

    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        notificationRepository.save(getNotification(receivedDTO));
        emailService.sendEmail(receivedDTO, null, null);
        return receivedDTO;
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        NotificationContent<UserRegistered> content = receivedDTO.content();
        UserRegistered registered = content.getChild();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .notificationCreatedAt(LocalDateTime.now())
                .details(NotificationDetails.builder()
                        .type(registered.getType().name())
                        .name(registered.getOwnerName())
                        .email(registered.getEmail())
                        .entityCreatedAt(registered.getEntityCreatedAt())
                        .build())
                .build();
    }
}
