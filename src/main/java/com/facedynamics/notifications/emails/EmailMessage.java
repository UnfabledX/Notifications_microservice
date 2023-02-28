package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationUserServiceDTO;
import lombok.Setter;
import org.apache.velocity.app.VelocityEngine;

@Setter
public abstract class EmailMessage implements MailTextBody{

    protected VelocityEngine engine;
    protected NotificationGetDTO receivedDTO;
    protected NotificationUserServiceDTO ownerDTO;
    protected String triggerUserName;
}
