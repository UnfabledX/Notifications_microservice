package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import org.springframework.stereotype.Component;

@Component("USER_PASSWORD_RESET_REQUEST")
public class PasswordResetRequestProcessor extends AbstractNotificationProcessor {
    @Override
    public NotificationResponseDTO process(NotificationDto dto) {
        return null;
    }
}
