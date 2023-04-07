package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.model.dto.CommentLiked;
import com.facedynamics.notifications.model.dto.NotificationContent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;

import static com.facedynamics.notifications.utils.TimeConverter.convert;

@Component
public class CommentLikedEmailMessage extends EmailMessage {

    public static final String NEW_COMMENT_LIKED = "Received NEW Likes to the comment!";

    private final Long delay;
    private final String emailTemplate;

    public CommentLikedEmailMessage(@Value("${source.mail.islike.delaymillis}")Long delay,
                                    @Value("${source.mail.template.comment-liked}")String emailTemplate) {
        this.delay = delay;
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody() {
        NotificationContent<CommentLiked> content = receivedDTO.content();
        CommentLiked commentLiked = content.getChild();
        String commentText = commentLiked.getCommentText();
        List<CommentLiked> events = (List<CommentLiked>) payload;

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getOwnerName());
        context.put("commentBody", commentText.length() > 50 ? commentText.substring(0, 50) : commentText);
        context.put("numberOfLikes", events.size());
        context.put("times", events.size() == 1 ? "time" : "times");
        context.put("min", delay / 60000);
        context.put("minutes", delay / 60000 == 1 ? "minute" : "minutes");
        context.put("likeCreatedAt", convert(commentLiked.getEntityCreatedAt()));
        Template template = engine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject() {
        return NEW_COMMENT_LIKED;
    }
}