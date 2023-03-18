package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PasswordResetRequest;

public class PasswordResetRequestCommand extends AbstractCommand {
    @Override
    public NotificationResponseDTO execute(NotificationDto receivedDTO) {
        PasswordResetRequest resetRequest = (PasswordResetRequest) receivedDTO.content();
        notificationRepository.save(getNotification(receivedDTO));
        emailService.sendEmail(receivedDTO, null, null);
        return NotificationResponseDTO.builder()
                .triggererName(resetRequest.getOwnerName())
                .type(resetRequest.getType())
                .createdAt(receivedDTO.createdAt())
                .build();
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        PasswordResetRequest resetRequest = (PasswordResetRequest) receivedDTO.content();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .createdAt(receivedDTO.createdAt())
                .updatedAt(receivedDTO.updatedAt())
                .details(NotificationDetails.builder()
                        .type(resetRequest.getType().name())
                        .build())
                .build();
    }
}
