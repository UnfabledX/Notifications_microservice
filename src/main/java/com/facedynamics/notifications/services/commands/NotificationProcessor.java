package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.controllers.UserEventService;
import com.facedynamics.notifications.repository.NotificationRepository;
import com.facedynamics.notifications.services.EmailService;
import lombok.Setter;

@Setter
public abstract class NotificationProcessor implements CommandInterface {
    protected UserEventService userEventService;
    protected EmailService emailService;
    protected NotificationRepository notificationRepository;
}
