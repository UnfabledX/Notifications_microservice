package com.facedynamics.notifications.securityexpression;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Auth {

    private final NotificationRepository notificationRepository;

    public boolean hasId(Long ownerUserId) {
        return Objects.equals(getCurrentUserId(), ownerUserId);
    }

    public boolean belongsToUser(Long notificationId) {
        Optional <Notification> notification = notificationRepository.findNotificationById(notificationId);
        Long notificationUserId = -1L;
        if (notification.isPresent()) {
            notificationUserId = notification.get().getOwnerId();
        }
        return Objects.equals(getCurrentUserId(), notificationUserId);
    }

    private static Long getCurrentUserId() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (Long) jwt.getClaims().get("identity_id");
    }
}
