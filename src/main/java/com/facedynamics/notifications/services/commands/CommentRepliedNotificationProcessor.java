package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.clients.UserApiClient;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.dto.CommentReplied;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.repository.NotificationRepository;
import com.facedynamics.notifications.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentRepliedNotificationProcessor implements NotificationProcessor {

    private final UserApiClient userApiClient;
    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        NotificationUserServiceDTO ownerDTO = userApiClient.getById(receivedDTO.recipientId());
        NotificationUserServiceDTO triggerUserDTO = userApiClient.getById(receivedDTO.createdById());
        notificationRepository.save(getNotification(receivedDTO));
        emailService.sendEmail(receivedDTO, ownerDTO, triggerUserDTO.getUsername());
        return receivedDTO;
    }

    private Notification getNotification(NotificationDto receivedDTO) {
        if (receivedDTO.content() instanceof CommentReplied replied) {
            return Notification.builder()
                    .ownerId(receivedDTO.recipientId())
                    .createdById(receivedDTO.createdById())
                    .notificationCreatedAt(LocalDateTime.now())
                    .details(NotificationDetails.builder()
                            .type(replied.getType().name())
                            .commentId(replied.getCommentId())
                            .replyId(replied.getReplyId())
                            .entityCreatedAt(replied.getEntityCreatedAt())
                            .build())
                    .build();
        } else {
            throw new IllegalArgumentException("Wrong type of the notification!");
        }
    }
}
