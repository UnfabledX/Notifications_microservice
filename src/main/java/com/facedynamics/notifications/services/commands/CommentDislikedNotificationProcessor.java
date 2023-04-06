package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.events.CommentDislikeEvent;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.CommentDisliked;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

public class CommentDislikedNotificationProcessor extends AbstractNotificationProcessor {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        NotificationUserServiceDTO ownerDTO = userEventService.findById(receivedDTO.recipientId());
        notificationRepository.save(getNotification(receivedDTO));
        applicationEventPublisher.publishEvent(new CommentDislikeEvent(this, receivedDTO, ownerDTO));
        return receivedDTO;
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        NotificationContent<CommentDisliked> content = receivedDTO.content();
        CommentDisliked commentDisliked = content.getChild();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .notificationCreatedAt(LocalDateTime.now())
                .details(NotificationDetails.builder()
                        .type(commentDisliked.getType().name())
                        .commentId(commentDisliked.getCommentId())
                        .entityCreatedAt(commentDisliked.getEntityCreatedAt())
                        .build())
                .build();
    }
}
