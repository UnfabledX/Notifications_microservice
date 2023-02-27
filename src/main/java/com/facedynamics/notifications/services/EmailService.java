package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationUserServiceDTO;

public interface EmailService {

    void sendEmail(NotificationGetDTO receivedDTO, NotificationUserServiceDTO ownerDTO,
                          String triggerUserName);
}
