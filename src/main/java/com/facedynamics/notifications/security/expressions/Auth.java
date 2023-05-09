package com.facedynamics.notifications.security.expressions;

import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Auth {

    private final NotificationRepository notificationRepository;

    public boolean hasId(Long ownerUserId) {
        return Objects.equals(getCurrentUserId(), ownerUserId);
    }

    public boolean belongsToUser(Long notificationId) {
        Long notificationUserId = notificationRepository
                .findNotificationById(notificationId)
                .map(Notification::getOwnerId)
                .orElse(-1L);
        return Objects.equals(getCurrentUserId(), notificationUserId);
    }

    private static Long getCurrentUserId() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (Long) jwt.getClaims().get("identity_id");
    }
}
