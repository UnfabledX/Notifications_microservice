package com.facedynamics.notifications.emails;

import com.facedynamics.notifications.events.PostLikeEvent;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.PostLiked;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;

@Component
public class PostLikedEmailMessage extends EmailMessage {

    public static final String NEW_POST_LIKED = "Received NEW Likes to the post!";

    private final Long delay;

    private final String emailTemplate;

    public PostLikedEmailMessage(@Value("${source.mail.islike.delaymillis}")    Long delay,
                                 @Value("${source.mail.template.post-liked}")String emailTemplate) {
        this.delay = delay;
        this.emailTemplate = emailTemplate;
    }

    @Override
    public StringWriter getLetterBody() {
        NotificationContent<PostLiked> content = receivedDTO.content();
        PostLiked postLiked = content.getChild();
        String postBody = postLiked.getPostText();
        List<PostLikeEvent> events = (List<PostLikeEvent>) payload;

        VelocityContext context = new VelocityContext();
        context.put("ownerName", ownerDTO.getOwnerName());
        context.put("postBody", postBody.length() > 50 ? postBody.substring(0, 50) : postBody);
        context.put("numberOfLikes", events.size());
        context.put("times", events.size() == 1 ? "time" : "times");
        context.put("min", delay / 60000);
        context.put("minutes", delay / 60000 == 1 ? "minute" : "minutes");
        context.put("likeCreatedAt", postLiked.getEntityCreatedAt());
        Template template = engine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer;
    }

    @Override
    public String getLetterSubject() {
        return NEW_POST_LIKED;
    }
}