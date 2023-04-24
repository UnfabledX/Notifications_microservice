package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.config.clients.UserApiClient;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PostCommented;
import com.facedynamics.notifications.repository.NotificationRepository;
import com.facedynamics.notifications.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostCommentedNotificationProcessor implements NotificationProcessor {

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
        NotificationContent<PostCommented> content = receivedDTO.content();
        PostCommented commented = content.getChild();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .notificationCreatedAt(LocalDateTime.now())
                .details(NotificationDetails.builder()
                        .type(commented.getType().name())
                        .postId(commented.getPostId())
                        .commentId(commented.getCommentId())
                        .entityCreatedAt(commented.getEntityCreatedAt())
                        .build())
                .build();
    }
}
