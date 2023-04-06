package com.facedynamics.notifications.config;

import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.services.commands.AbstractNotificationProcessor;
import com.facedynamics.notifications.services.commands.CommentDislikedNotificationProcessor;
import com.facedynamics.notifications.services.commands.CommentLikedNotificationProcessor;
import com.facedynamics.notifications.services.commands.CommentRepliedNotificationProcessor;
import com.facedynamics.notifications.services.commands.FollowedByNotificationProcessor;
import com.facedynamics.notifications.services.commands.PostCommentedNotificationProcessor;
import com.facedynamics.notifications.services.commands.PostDislikedNotificationProcessor;
import com.facedynamics.notifications.services.commands.PostLikedNotificationProcessor;
import com.facedynamics.notifications.services.commands.UserRegisteredNotificationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.*;

@Configuration
public class ProcessorConfiguration {

    @Bean(name = "processor")
    public Map<NotificationContent.Type, AbstractNotificationProcessor> getProcessor() {
        return Map.of(
                USER_REGISTERED,    userRegisteredNotificationProcessor(),
                POST_COMMENTED,     postCommentedNotificationProcessor(),
                COMMENT_REPLIED,    commentRepliedNotificationProcessor(),
                FOLLOWS,            followedByNotificationProcessor(),
                POST_LIKED,         postLikedNotificationProcessor(),
                POST_DISLIKED,      postDislikedNotificationProcessor(),
                COMMENT_LIKED,      commentLikedNotificationProcessor(),
                COMMENT_DISLIKED,   commentDislikedNotificationProcessor()
        );
    }

    @Bean
    public UserRegisteredNotificationProcessor userRegisteredNotificationProcessor() {
        return new UserRegisteredNotificationProcessor();
    }

    @Bean
    public PostCommentedNotificationProcessor postCommentedNotificationProcessor() {
        return new PostCommentedNotificationProcessor();
    }

    @Bean
    public CommentRepliedNotificationProcessor commentRepliedNotificationProcessor() {
        return new CommentRepliedNotificationProcessor();
    }

    @Bean
    public FollowedByNotificationProcessor followedByNotificationProcessor() {
        return new FollowedByNotificationProcessor();
    }

    @Bean
    public PostLikedNotificationProcessor postLikedNotificationProcessor() {
        return new PostLikedNotificationProcessor();
    }

    @Bean
    public PostDislikedNotificationProcessor postDislikedNotificationProcessor() {
        return new PostDislikedNotificationProcessor();
    }

    @Bean
    public CommentLikedNotificationProcessor commentLikedNotificationProcessor() {
        return new CommentLikedNotificationProcessor();
    }

    @Bean
    public CommentDislikedNotificationProcessor commentDislikedNotificationProcessor() {
        return new CommentDislikedNotificationProcessor();
    }
}
