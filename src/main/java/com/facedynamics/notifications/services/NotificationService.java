package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NotificationService {

    Page<Notification> getAllNotificationsByUserId(int ownerId, Pageable pageable);

    void deleteAllNotificationsByOwnerId(int ownerId);

    Long deleteNotificationById(long notificationId);

    NotificationResponseDTO createNotification(NotificationDto notification);

}
