package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.CommentReplied;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;

public class CommentRepliedEmailMessage extends EmailMessage {

    public static final String NEW_REPLY = "Received a NEW Reply!";

    @Override
    public StringWriter getLetterBody() {
        CommentReplied created = (CommentReplied) receivedDTO.content();
        String replyBody = created.getReplyText();
        String commentBody = created.getCommentText();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getOwnerName());
        context.put("triggererUsername", triggerUserName);
        context.put("replyBody", replyBody.length() > 40 ? replyBody.substring(0, 40) : replyBody);
        context.put("commentBody", commentBody.length() > 50 ? commentBody.substring(0, 50) : commentBody);
        context.put("replyCreatedAt", receivedDTO.createdAt());
        Template template = engine.getTemplate("src/main/resources/velocity/email-reply.vm");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject() {
        return NEW_REPLY;
    }
}
