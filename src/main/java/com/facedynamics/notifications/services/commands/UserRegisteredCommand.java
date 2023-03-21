package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.UserRegistered;

public class UserRegisteredCommand extends AbstractCommand {
    @Override
    public NotificationResponseDTO execute(NotificationDto receivedDTO) {
        NotificationContent<UserRegistered> content = receivedDTO.content();
        UserRegistered registered = content.getChild();
        notificationRepository.save(getNotification(receivedDTO));
        emailService.sendEmail(receivedDTO, null, null);
        return NotificationResponseDTO.builder()
                .triggererName(registered.getOwnerName())
                .type(registered.getType())
                .createdAt(receivedDTO.createdAt())
                .build();
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        NotificationContent<UserRegistered> content = receivedDTO.content();
        UserRegistered registered = content.getChild();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .createdAt(receivedDTO.createdAt())
                .updatedAt(receivedDTO.updatedAt())
                .details(NotificationDetails.builder()
                        .type(registered.getType().name())
                        .build())
                .build();
    }
}
