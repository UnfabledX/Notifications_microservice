package com.facedynamics.notifications.config;

import com.facedynamics.notifications.emails.CommentDislikedEmailMessage;
import com.facedynamics.notifications.emails.CommentLikedEmailMessage;
import com.facedynamics.notifications.emails.CommentRepliedEmailMessage;
import com.facedynamics.notifications.emails.EmailMessage;
import com.facedynamics.notifications.emails.FollowedByEmailMessage;
import com.facedynamics.notifications.emails.PostCommentedEmailMessage;
import com.facedynamics.notifications.emails.PostDislikedEmailMessage;
import com.facedynamics.notifications.emails.PostLikedEmailMessage;
import com.facedynamics.notifications.emails.UserRegisteredEmailMessage;
import com.facedynamics.notifications.model.dto.NotificationContent;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.*;

@Configuration
@RequiredArgsConstructor
public class MailConfiguration {

    private final UserRegisteredEmailMessage userRegisteredEmailMessage;
    private final PostCommentedEmailMessage postCommentedEmailMessage;
    private final CommentRepliedEmailMessage commentRepliedEmailMessage;
    private final FollowedByEmailMessage followedByEmailMessage;
    private final PostLikedEmailMessage postLikedEmailMessage;
    private final PostDislikedEmailMessage postDislikedEmailMessage;
    private final CommentLikedEmailMessage commentLikedEmailMessage;
    private final CommentDislikedEmailMessage commentDislikedEmailMessage;

    @Bean(name = "engine")
    public VelocityEngine getVelocityEngine(){
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADERS,"classpath");
        engine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
        engine.init();
        return engine;
    }

    @Bean(name = "mails")
    public Map<NotificationContent.Type, EmailMessage> getMails() {
        return Map.of(
                USER_REGISTERED,    userRegisteredEmailMessage,
                POST_COMMENTED,     postCommentedEmailMessage,
                COMMENT_REPLIED,    commentRepliedEmailMessage,
                FOLLOWS,            followedByEmailMessage,
                POST_LIKED,         postLikedEmailMessage,
                POST_DISLIKED,      postDislikedEmailMessage,
                COMMENT_LIKED,      commentLikedEmailMessage,
                COMMENT_DISLIKED,   commentDislikedEmailMessage
        );
    }
}

