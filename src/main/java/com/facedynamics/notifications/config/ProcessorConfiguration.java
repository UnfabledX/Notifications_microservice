package com.facedynamics.notifications.config;

import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.services.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.*;

@Configuration
public class ProcessorConfiguration {

    @Autowired
    private UserRegisteredNotificationProcessor userRegisteredNotificationProcessor;
    @Autowired
    private PasswordResetRequestProcessor passwordResetRequestProcessor;
    @Autowired
    private PostCommentedNotificationProcessor postCommentedNotificationProcessor;
    @Autowired
    private CommentRepliedNotificationProcessor commentRepliedNotificationProcessor;
    @Autowired
    private FollowedByNotificationProcessor followedByNotificationProcessor;
    @Autowired
    private PostLikedNotificationProcessor postLikedNotificationProcessor;
    @Autowired
    private PostDislikedNotificationProcessor postDislikedNotificationProcessor;
    @Autowired
    private CommentLikedNotificationProcessor commentLikedNotificationProcessor;
    @Autowired
    private CommentDislikedNotificationProcessor commentDislikedNotificationProcessor;

    @Bean(name = "processor")
    public Map<NotificationContent.Type, AbstractNotificationProcessor> getProcessor() {
        return Map.of(
                USER_REGISTERED,                userRegisteredNotificationProcessor,
                USER_PASSWORD_RESET_REQUEST,    passwordResetRequestProcessor,
                POST_COMMENTED,                 postCommentedNotificationProcessor,
                COMMENT_REPLIED,                commentRepliedNotificationProcessor,
                FOLLOWED_BY,                    followedByNotificationProcessor,
                POST_LIKED,                     postLikedNotificationProcessor,
                POST_DISLIKED,                  postDislikedNotificationProcessor,
                COMMENT_LIKED,                  commentLikedNotificationProcessor,
                COMMENT_DISLIKED,               commentDislikedNotificationProcessor
        );
    }
}
