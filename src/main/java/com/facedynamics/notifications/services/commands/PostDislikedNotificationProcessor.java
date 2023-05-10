package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.clients.UserApiClient;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.dto.PostDisliked;
import com.facedynamics.notifications.events.PostDislikeEvent;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostDislikedNotificationProcessor implements NotificationProcessor {

    private ApplicationEventPublisher applicationEventPublisher;
    private UserApiClient userEventService;
    private NotificationRepository notificationRepository;

    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        NotificationUserServiceDTO ownerDTO = userEventService.getById(receivedDTO.recipientId());
        notificationRepository.save(getNotification(receivedDTO));
        applicationEventPublisher.publishEvent(new PostDislikeEvent(this, receivedDTO, ownerDTO));
        return receivedDTO;
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        if (receivedDTO.content() instanceof PostDisliked postDisliked) {
            return Notification.builder()
                    .ownerId(receivedDTO.recipientId())
                    .createdById(receivedDTO.createdById())
                    .notificationCreatedAt(LocalDateTime.now())
                    .details(NotificationDetails.builder()
                            .type(postDisliked.getType().name())
                            .postId(postDisliked.getPostId())
                            .entityCreatedAt(postDisliked.getEntityCreatedAt())
                            .build())
                    .build();
        } else {
            throw new IllegalArgumentException("Wrong type of the notification!");
        }
    }
}