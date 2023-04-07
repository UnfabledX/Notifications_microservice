package com.facedynamics.notifications.config;

import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.services.commands.AbstractNotificationProcessor;
import com.facedynamics.notifications.services.commands.CommentDislikedNotificationProcessor;
import com.facedynamics.notifications.services.commands.CommentLikedNotificationProcessor;
import com.facedynamics.notifications.services.commands.CommentRepliedNotificationProcessor;
import com.facedynamics.notifications.services.commands.WaitingApprovalNotificationProcessor;
import com.facedynamics.notifications.services.commands.FollowedByNotificationProcessor;
import com.facedynamics.notifications.services.commands.PostCommentedNotificationProcessor;
import com.facedynamics.notifications.services.commands.PostDislikedNotificationProcessor;
import com.facedynamics.notifications.services.commands.PostLikedNotificationProcessor;
import com.facedynamics.notifications.services.commands.UserRegisteredNotificationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.*;

@Configuration
@RequiredArgsConstructor
public class ProcessorConfiguration {

    private final UserRegisteredNotificationProcessor userRegisteredNotificationProcessor;
    private final PostCommentedNotificationProcessor postCommentedNotificationProcessor;
    private final CommentRepliedNotificationProcessor commentRepliedNotificationProcessor;
    private final WaitingApprovalNotificationProcessor waitingApprovalNotificationProcessor;
    private final FollowedByNotificationProcessor followedByNotificationProcessor;
    private final PostLikedNotificationProcessor postLikedNotificationProcessor;
    private final PostDislikedNotificationProcessor postDislikedNotificationProcessor;
    private final CommentLikedNotificationProcessor commentLikedNotificationProcessor;
    private final CommentDislikedNotificationProcessor commentDislikedNotificationProcessor;

    @Bean(name = "processor")
    public Map<NotificationContent.Type, AbstractNotificationProcessor> getProcessor() {
        return Map.of(
                USER_REGISTERED,    userRegisteredNotificationProcessor,
                POST_COMMENTED,     postCommentedNotificationProcessor,
                COMMENT_REPLIED,    commentRepliedNotificationProcessor,
                WAITING_APPROVE,    waitingApprovalNotificationProcessor,
                FOLLOWS,            followedByNotificationProcessor,
                POST_LIKED,         postLikedNotificationProcessor,
                POST_DISLIKED,      postDislikedNotificationProcessor,
                COMMENT_LIKED,      commentLikedNotificationProcessor,
                COMMENT_DISLIKED,   commentDislikedNotificationProcessor
        );
    }
}
