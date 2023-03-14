package com.facedynamics.notifications.services.commands;

import com.facedynamics.notifications.controllers.UserEventService;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.repository.NotificationRepository;
import com.facedynamics.notifications.services.EmailService;
import lombok.RequiredArgsConstructor;

/**
 * Class CommandFactory provides corresponding
 * command depending on what notification is to create.
 *
 * @author Oleksii Synelnyk
 */
@RequiredArgsConstructor
public class CommandFactory {

    private final UserEventService userEventService;
    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    /**
     * Creates command from enum NotificationContent.Type
     * that fulfills the task of creating specific notifications
     *
     * @param type enum instance which indicates what type of notification
     *             to create.
     * @return Command that corresponds to specific enum.
     */
    public AbstractCommand createCommand(NotificationContent.Type type) {
        AbstractCommand command = null;
        switch (type) {
            case USER_REGISTERED             -> command = new UserRegisteredCommand();
            case USER_PASSWORD_RESET_REQUEST -> command = new PasswordResetRequestCommand();
            case POST_COMMENTED              -> command = new PostCommentedCommand();
            case COMMENT_REPLIED             -> command = new CommentRepliedCommand();
            case FOLLOWED_BY                 -> command = new FollowedByCommand();
            case SUBSCRIBED_BY               -> command = new SubscribedByCommand();
        }
        if (command != null) {
            command.setUserEventService(userEventService);
            command.setEmailService(emailService);
            command.setNotificationRepository(notificationRepository);
        }
        return command;
    }
}
