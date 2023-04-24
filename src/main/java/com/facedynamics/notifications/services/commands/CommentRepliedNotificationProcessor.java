package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.clients.UserApiClient;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.CommentReplied;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
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

    private static Notification getNotification(NotificationDto receivedDTO) {
        NotificationContent<CommentReplied> content = receivedDTO.content();
        CommentReplied replied = content.getChild();
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
    }
}
