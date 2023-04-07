package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.events.CommentLikeEvent;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.CommentLiked;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentLikedNotificationProcessor extends AbstractNotificationProcessor {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        NotificationUserServiceDTO ownerDTO = userEventService.findById(receivedDTO.recipientId());
        notificationRepository.save(getNotification(receivedDTO));
        applicationEventPublisher.publishEvent(new CommentLikeEvent(this, receivedDTO, ownerDTO));
        return receivedDTO;
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        NotificationContent<CommentLiked> content = receivedDTO.content();
        CommentLiked commentLiked = content.getChild();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .notificationCreatedAt(LocalDateTime.now())
                .details(NotificationDetails.builder()
                        .type(commentLiked.getType().name())
                        .commentId(commentLiked.getCommentId())
                        .entityCreatedAt(commentLiked.getEntityCreatedAt())
                        .build())
                .build();
    }
}
