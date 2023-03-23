package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.dto.NotificationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NotificationService {

    Page<Notification> getAllNotificationsByUserId(Long ownerId, Pageable pageable);

    void deleteAllNotificationsByOwnerId(Long ownerId);

    Long deleteNotificationById(Long notificationId);

    NotificationDto createNotification(NotificationDto notification);

}
