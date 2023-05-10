package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.dto.FollowedBy;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

import static com.facedynamics.notifications.utils.TimeConverter.convert;

@Component
public class FollowedByEmailMessage extends EmailMessage {

    public static final String NEW_FOLLOW = "You have a NEW Follow!";

    private final String emailTemplate;

    public FollowedByEmailMessage(
            VelocityEngine engine,
            @Value("${source.mail.template.followed-by}") String emailTemplate) {
        super(engine);
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO, Object payload) {
        if (receivedDTO.content() instanceof FollowedBy followedBy) {
            VelocityContext context = new VelocityContext();
            context.put("ownerName", followedBy.getRecipientName());
            context.put("triggererUsername", followedBy.getCreatedByName());
            context.put("followCreatedAt", convert(followedBy.getEntityCreatedAt()));
            Template template = engine.getTemplate(emailTemplate);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer;
        } else {
            throw new IllegalArgumentException("Wrong type of the notification!");
        }
    }

    @Override
    public String getLetterSubject() {
        return NEW_FOLLOW;
    }
}
