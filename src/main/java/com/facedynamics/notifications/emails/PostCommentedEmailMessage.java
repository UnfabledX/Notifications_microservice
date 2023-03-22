package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.PostCommented;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.io.StringWriter;

@Component
public class PostCommentedEmailMessage extends EmailMessage {

    public static final String NEW_COMMENT = "Received a NEW Comment!";

    @Value("${source.mail.template.post-commented}")
    private String emailTemplate;

    @Override
    public StringWriter getLetterBody() {
        PostCommented created = (PostCommented) receivedDTO.content();
        String commentBody = created.getCommentText();
        String postBody = created.getPostText();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getOwnerName());
        context.put("triggererUsername", triggerUserName);
        context.put("commentBody", commentBody.length() > 40 ? commentBody.substring(0, 40) : commentBody);
        context.put("postBody", postBody.length() > 50 ? postBody.substring(0, 50) : postBody);
        context.put("commentCreatedAt", receivedDTO.createdAt());
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
