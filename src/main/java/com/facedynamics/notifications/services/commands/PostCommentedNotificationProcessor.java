package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PostCommented;
import org.springframework.stereotype.Component;

@Component("POST_COMMENTED")
public class PostCommentedNotificationProcessor extends NotificationProcessor {

    @Override
    public NotificationResponseDTO execute(NotificationDto receivedDTO) {
        NotificationUserServiceDTO ownerDTO = userEventService.getUserById(receivedDTO.recipientId());
        NotificationUserServiceDTO triggerUserDTO = userEventService.getUserById(receivedDTO.createdById());
        notificationRepository.save(getNotification(receivedDTO));
        emailService.sendEmail(receivedDTO, ownerDTO, triggerUserDTO.getUsername());
        return NotificationResponseDTO.builder()
                .triggererName(triggerUserDTO.getUsername())
                .type(receivedDTO.content().getType())
                .createdAt(receivedDTO.createdAt())
                .build();
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        PostCommented commented = (PostCommented) receivedDTO.content();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .createdAt(receivedDTO.createdAt())
                .updatedAt(receivedDTO.updatedAt())
                .details(NotificationDetails.builder()
                        .type(commented.getType().name())
                        .postId(commented.getPostId())
                        .commentId(commented.getCommentId())
                        .build())
                .build();
    }
}