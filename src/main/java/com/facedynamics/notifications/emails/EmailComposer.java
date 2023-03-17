package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.Map;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.*;


public class EmailComposer {

    private static final VelocityEngine engine;

    static {
        engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER,"classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();
    }

    private static final Map<NotificationContent.Type, EmailMessage> mapOfMails = Map.of(
            USER_REGISTERED,                new UserRegisteredEmailMessage(),
            USER_PASSWORD_RESET_REQUEST,    new PasswordResetRequestEmailMessage(),
            POST_COMMENTED,                 new PostCommentedEmailMessage(),
            COMMENT_REPLIED,                new CommentRepliedEmailMessage(),
            FOLLOWED_BY,                    new FollowedByEmailMessage(),
            SUBSCRIBED_BY,                  new SubscribedByEmailMessage()
    );

    public static StringWriter getWriter(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                                         String triggerUserName) {
        EmailMessage specificEmailMessage = getEmailMessage(receivedDTO);
        specificEmailMessage.setEngine(engine);
        specificEmailMessage.setReceivedDTO(receivedDTO);
        specificEmailMessage.setOwnerDTO(ownerDTO);
        specificEmailMessage.setTriggerUserName(triggerUserName);
        return specificEmailMessage.getLetterBody();
    }

    public static String getSubject(NotificationDto receivedDTO) {
        EmailMessage specificEmailMessage = getEmailMessage(receivedDTO);
        return specificEmailMessage.getLetterSubject();
    }

    private static EmailMessage getEmailMessage(NotificationDto receivedDTO) {
        NotificationContent.Type type = receivedDTO.content().getType();
        return mapOfMails.get(type);
    }
}
