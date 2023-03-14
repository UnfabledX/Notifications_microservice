package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.CommentCreated;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;


public class CommentCreatedEmailMessage extends EmailMessage {

    public static final String NEW_COMMENT = "Received a NEW Comment!";

    @Override
    public StringWriter getLetterBody() {
        CommentCreated created = (CommentCreated) receivedDTO.content();
        String commentBody = created.getCommentText();
        String postBody = created.getPostText();

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getName());
        context.put("triggererUsername", triggerUserName);
        context.put("commentBody", commentBody.length() > 40 ? commentBody.substring(0, 40) : commentBody);
        context.put("postBody", postBody.length() > 50 ? postBody.substring(0, 50) : postBody);
        context.put("commentCreatedAt", receivedDTO.createdAt());
        Template template = engine.getTemplate("target/classes/velocity/email-comment.vm");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }
//"src/main/resources/velocity/email-comment.vm"
    @Override
    public String getLetterSubject() {
        return NEW_COMMENT;
    }
}