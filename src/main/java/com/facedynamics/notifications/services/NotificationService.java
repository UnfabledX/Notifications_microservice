package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationResponseDTO;
import org.springframework.data.domain.Page;

public interface NotificationService {

    Page<Notification> getAllNotificationsByUserId(int page, int ownerId);

    void deleteAllNotificationsByOwnerId(int ownerId);

    Long deleteNotificationById(long notificationId);

    NotificationResponseDTO createNotification(NotificationGetDTO notification);

}
