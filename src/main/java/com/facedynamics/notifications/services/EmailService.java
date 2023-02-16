package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationUserServiceDTO;

public interface EmailService {

    void sendCommentEmail(NotificationGetDTO receivedDTO, NotificationUserServiceDTO ownerDTO,
                          String triggerUserName);

    void sendReplyEmail(NotificationGetDTO receivedDTO, NotificationUserServiceDTO ownerDTO,
                               String triggerUserName);
}
