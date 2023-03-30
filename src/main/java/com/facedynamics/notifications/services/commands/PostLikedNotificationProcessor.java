package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.events.PostLikeEvent;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PostLiked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostLikedNotificationProcessor extends AbstractNotificationProcessor {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public NotificationDto process(NotificationDto receivedDTO) {
        NotificationUserServiceDTO ownerDTO = userEventService.findById(receivedDTO.recipientId());
        notificationRepository.save(getNotification(receivedDTO));
        applicationEventPublisher.publishEvent(new PostLikeEvent(this, receivedDTO, ownerDTO));
        return receivedDTO;
    }

    private static Notification getNotification(NotificationDto receivedDTO) {
        NotificationContent<PostLiked> content = receivedDTO.content();
        PostLiked postLiked = content.getChild();
        return Notification.builder()
                .ownerId(receivedDTO.recipientId())
                .createdById(receivedDTO.createdById())
                .notificationCreatedAt(LocalDateTime.now())
                .details(NotificationDetails.builder()
                        .type(postLiked.getType().name())
                        .postId(postLiked.getPostId())
                        .entityCreatedAt(postLiked.getEntityCreatedAt())
                        .build())
                .build();
    }
}
