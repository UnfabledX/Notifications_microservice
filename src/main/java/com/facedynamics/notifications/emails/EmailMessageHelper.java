package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.NotificationType;
import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationUserServiceDTO;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

import static com.facedynamics.notifications.model.NotificationType.*;

public class EmailMessageHelper {

    private static final VelocityEngine engine;

    static {
        engine = new VelocityEngine();
        engine.init();
    }

    private static final Map<NotificationType, EmailMessage> mapOfMails = Map.of(
            REGISTRATION,   new RegistrationEmailMessage(),
            RESET_PASSWORD, new ResetPasswordEmailMessage(),
            COMMENT,        new CommentEmailMessage(),
            REPLY,          new ReplyEmailMessage(),
            FOLLOW,         new FollowEmailMessage(),
            SUBSCRIPTION,   new SubscriptionEmailMessage()
    );

    public static StringWriter getWriter(NotificationGetDTO receivedDTO, NotificationUserServiceDTO ownerDTO,
                                         String triggerUserName) {
        EmailMessage specificEmailMessage = getEmailMessage(receivedDTO);
        specificEmailMessage.setEngine(engine);
        specificEmailMessage.setReceivedDTO(receivedDTO);
        specificEmailMessage.setOwnerDTO(ownerDTO);
        specificEmailMessage.setTriggerUserName(triggerUserName);
        return specificEmailMessage.getLetterBody();
    }

    public static String getSubject(NotificationGetDTO receivedDTO) {
        EmailMessage specificEmailMessage = getEmailMessage(receivedDTO);
        return specificEmailMessage.getLetterSubject();
    }

    private static EmailMessage getEmailMessage(NotificationGetDTO receivedDTO) {
        NotificationType type = getType(receivedDTO.getNotificationType());
        return mapOfMails.get(type);
    }
}
