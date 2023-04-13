package com.facedynamics.notifications.config;

import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.services.commands.AbstractNotificationProcessor;
import com.facedynamics.notifications.services.commands.CommentRepliedNotificationProcessor;
import com.facedynamics.notifications.services.commands.FollowedByNotificationProcessor;
import com.facedynamics.notifications.services.commands.PostCommentedNotificationProcessor;
import com.facedynamics.notifications.services.commands.UserRegisteredNotificationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.*;

@Configuration
@RequiredArgsConstructor
public class ProcessorConfiguration {

    @Bean(name = "processor")
    public Map<NotificationContent.Type, AbstractNotificationProcessor> getProcessor(
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
