package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.CommentDisliked;
import com.facedynamics.notifications.model.dto.NotificationContent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;

@Component
public class CommentDislikedEmailMessage extends EmailMessage {

    public static final String NEW_COMMENT_DISLIKED = "Received NEW Dislikes to the comment!";

    @Value("${source.mail.islike.delaymillis}")
    private Long delay;

    @Value("${source.mail.template.comment-disliked}")
    private String emailTemplate;

    @Override
    public StringWriter getLetterBody() {
        NotificationContent<CommentDisliked> content = receivedDTO.content();
        CommentDisliked commentDisliked = content.getChild();
        String commentText = commentDisliked.getCommentText();
        List<CommentDisliked> events = (List<CommentDisliked>) payload;

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getOwnerName());
        context.put("commentBody", commentText.length() > 50 ? commentText.substring(0, 50) : commentText);
        context.put("numberOfDislikes", events.size());
        context.put("times", events.size() == 1 ? "time" : "times");
        context.put("min", delay / 60000);
        context.put("minutes", delay / 60000 == 1 ? "minute" : "minutes");
        context.put("likeCreatedAt", commentDisliked.getEntityCreatedAt());
        Template template = engine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject() {
        return NEW_COMMENT_DISLIKED;
    }
}