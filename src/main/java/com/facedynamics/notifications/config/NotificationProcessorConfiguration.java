package com.facedynamics.notifications.config;

import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.services.commands.CommentRepliedNotificationProcessor;
import com.facedynamics.notifications.services.commands.FollowedByNotificationProcessor;
import com.facedynamics.notifications.services.commands.NotificationProcessor;
import com.facedynamics.notifications.services.commands.PostCommentedNotificationProcessor;
import com.facedynamics.notifications.services.commands.UserRegisteredNotificationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.COMMENT_REPLIED;
import static com.facedynamics.notifications.model.dto.NotificationContent.Type.FOLLOWED_BY;
import static com.facedynamics.notifications.model.dto.NotificationContent.Type.POST_COMMENTED;
import static com.facedynamics.notifications.model.dto.NotificationContent.Type.USER_REGISTERED;

@Configuration
@RequiredArgsConstructor
public class NotificationProcessorConfiguration {

    @Bean
    public Map<NotificationContent.Type, NotificationProcessor> getProcessor(
            UserRegisteredNotificationProcessor userRegisteredNotificationProcessor,
            PostCommentedNotificationProcessor postCommentedNotificationProcessor,
            CommentRepliedNotificationProcessor commentRepliedNotificationProcessor,
            FollowedByNotificationProcessor followedByNotificationProcessor
    ) {
        return Map.of(
                USER_REGISTERED,    userRegisteredNotificationProcessor,
                POST_COMMENTED,     postCommentedNotificationProcessor,
                COMMENT_REPLIED,    commentRepliedNotificationProcessor,
                FOLLOWED_BY,        followedByNotificationProcessor
        );
    }
}
