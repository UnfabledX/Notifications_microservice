package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.*;

@Component
@RequiredArgsConstructor
public class EmailComposer {

    private final VelocityEngine engine;

    private static final Map<NotificationContent.Type, EmailMessage> mapOfMails = Map.of(
            USER_REGISTERED,                new UserRegisteredEmailMessage(),
            USER_PASSWORD_RESET_REQUEST,    new PasswordResetRequestEmailMessage(),
            POST_COMMENTED,                 new PostCommentedEmailMessage(),
            COMMENT_REPLIED,                new CommentRepliedEmailMessage(),
            FOLLOWED_BY,                    new FollowedByEmailMessage(),
            SUBSCRIBED_BY,                  new SubscribedByEmailMessage()
    );

    public StringWriter getWriter(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                                         String triggerUserName) {
        EmailMessage specificEmailMessage = getEmailMessage(receivedDTO);
        specificEmailMessage.setEngine(engine);
        specificEmailMessage.setReceivedDTO(receivedDTO);
        specificEmailMessage.setOwnerDTO(ownerDTO);
        specificEmailMessage.setTriggerUserName(triggerUserName);
        return specificEmailMessage.getLetterBody();
    }

    public String getSubject(NotificationDto receivedDTO) {
        EmailMessage specificEmailMessage = getEmailMessage(receivedDTO);
        return specificEmailMessage.getLetterSubject();
    }

    private EmailMessage getEmailMessage(NotificationDto receivedDTO) {
        NotificationContent.Type type = receivedDTO.content().getType();
        return mapOfMails.get(type);
    }
}
