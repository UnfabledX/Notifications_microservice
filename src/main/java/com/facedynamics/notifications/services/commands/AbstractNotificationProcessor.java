package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.controllers.UserEventService;
import com.facedynamics.notifications.repository.NotificationRepository;
import com.facedynamics.notifications.services.EmailService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Setter
public abstract class AbstractNotificationProcessor implements NotificationProcessor {
    @Autowired
    protected UserEventService userEventService;
    @Autowired
    protected EmailService emailService;
    @Autowired
    protected NotificationRepository notificationRepository;
}
