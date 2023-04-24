package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.dto.CommentReplied;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class CommentRepliedEmailMessage extends EmailMessage {

    public static final String NEW_REPLY = "Received a NEW Reply!";

    private final String emailTemplate;

    public CommentRepliedEmailMessage(@Value("${source.mail.template.comment-replied}") String emailTemplate,
                                      VelocityEngine engine) {
        super(engine);
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                                      Object payload) {
        CommentReplied created = (CommentReplied) receivedDTO.content();
        String replyBody = created.getReplyText();
        String commentBody = created.getCommentText();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getOwnerName());
        context.put("triggererUsername", payload);
        context.put("replyBody", replyBody.length() > 40 ? replyBody.substring(0, 40) : replyBody);
        context.put("commentBody", commentBody.length() > 50 ? commentBody.substring(0, 50) : commentBody);
        context.put("replyCreatedAt", created.getEntityCreatedAt());
        Template template = engine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject() {
        return NEW_REPLY;
    }
}
