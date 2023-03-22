package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.CommentReplied;
import com.facedynamics.notifications.model.dto.NotificationDto;
import org.springframework.stereotype.Component;

@Component("COMMENT_REPLIED")
public class CommentRepliedNotificationProcessor extends AbstractNotificationProcessor {

    @Override
    public NotificationResponseDTO process(NotificationDto receivedDTO) {
        NotificationUserServiceDTO ownerDTO = userEventService.getUserById(receivedDTO.recipientId());
        NotificationUserServiceDTO triggerUserDTO = userEventService.getUserById(receivedDTO.createdById());
        emailService.sendEmail(receivedDTO, ownerDTO, triggerUserDTO.getUsername());
        notificationRepository.save(getNotification(receivedDTO));
        return NotificationResponseDTO.builder()
                .triggererName(triggerUserDTO.getUsername())
                .type(receivedDTO.content().getType())
                .createdAt(receivedDTO.createdAt())
                .build();
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        CommentReplied replied = (CommentReplied) receivedDTO.content();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .createdAt(receivedDTO.createdAt())
                .updatedAt(receivedDTO.updatedAt())
                .details(NotificationDetails.builder()
                        .commentId(replied.getCommentId())
                        .replyId(replied.getReplyId())
                        .build())
                .build();
    }
}
