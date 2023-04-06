package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.dto.FollowedBy;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;

import java.time.LocalDateTime;

public class FollowedByNotificationProcessor extends AbstractNotificationProcessor {

    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        notificationRepository.save(getNotification(receivedDTO));
        emailService.sendEmail(receivedDTO, null, null);
        return receivedDTO;
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        NotificationContent<FollowedBy> content = receivedDTO.content();
        FollowedBy registered = content.getChild();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .notificationCreatedAt(LocalDateTime.now())
                .details(NotificationDetails.builder()
                        .type(registered.getType().name())
                        .entityCreatedAt(registered.getEntityCreatedAt())
                        .build())
                .build();
    }
}
