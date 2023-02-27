package com.facedynamics.notifications.utils.emails;

import com.facedynamics.notifications.model.NotificationType;
import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationUserServiceDTO;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

import static com.facedynamics.notifications.model.NotificationType.*;
import static com.facedynamics.notifications.model.NotificationType.SUBSCRIPTION;
import static com.facedynamics.notifications.utils.Constants.*;

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

    private static final Map<NotificationType, String> mapOfTopics = Map.of(
            REGISTRATION,   NEW_REGISTRATION,
            RESET_PASSWORD, NEW_RESET_PASSWORD,
            COMMENT,        NEW_COMMENT,
            REPLY,          NEW_REPLY,
            FOLLOW,         NEW_FOLLOW,
            SUBSCRIPTION,   NEW_SUBSCRIPTION
    );

    public static StringWriter getWriter(NotificationGetDTO receivedDTO, NotificationUserServiceDTO ownerDTO,
                                         String triggerUserName) {
        NotificationType type = getType(receivedDTO.getNotificationType());
        EmailMessage specificEmailMessage = mapOfMails.get(type);
        specificEmailMessage.setEngine(engine);
        specificEmailMessage.setReceivedDTO(receivedDTO);
        specificEmailMessage.setOwnerDTO(ownerDTO);
        specificEmailMessage.setTriggerUserName(triggerUserName);
        return specificEmailMessage.getLetterBody();
    }

    public static String getTopic(NotificationGetDTO receivedDTO) {
        NotificationType type = getType(receivedDTO.getNotificationType());
        return mapOfTopics.get(type);
    }
}
