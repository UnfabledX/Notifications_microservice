package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.PostCommented;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class PostCommentedEmailMessage extends EmailMessage {

    public static final String NEW_COMMENT = "Received a NEW Comment!";

    private final String emailTemplate;

    public PostCommentedEmailMessage(@Value("${source.mail.template.post-commented}") String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody() {
        NotificationContent<PostCommented> content = receivedDTO.content();
        PostCommented created = content.getChild();
        String commentBody = created.getCommentText();
        String postBody = created.getPostText();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getOwnerName());
        context.put("triggererUsername", payload);
        context.put("commentBody", commentBody.length() > 40 ? commentBody.substring(0, 40) : commentBody);
        context.put("postBody", postBody.length() > 50 ? postBody.substring(0, 50) : postBody);
        context.put("commentCreatedAt", created.getEntityCreatedAt());
        Template template = engine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject() {
        return NEW_COMMENT;
    }
}
