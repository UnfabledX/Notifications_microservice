package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.events.PostDislikeEvent;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PostDisliked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class PostDislikedNotificationProcessor extends AbstractNotificationProcessor {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        NotificationUserServiceDTO ownerDTO = userEventService.findById(receivedDTO.recipientId());
        notificationRepository.save(getNotification(receivedDTO));
        applicationEventPublisher.publishEvent(new PostDislikeEvent(this, receivedDTO, ownerDTO));
        return receivedDTO;
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        NotificationContent<PostDisliked> content = receivedDTO.content();
        PostDisliked postDisliked = content.getChild();
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
    }
}
