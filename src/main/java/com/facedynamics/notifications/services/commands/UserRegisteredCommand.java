package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import org.springframework.stereotype.Component;

@Component("USER_REGISTERED_COMMAND")
public class UserRegisteredCommand extends NotificationProcessor {
    @Override
    public NotificationResponseDTO execute(NotificationDto dto) {
        return null;
    }
}
