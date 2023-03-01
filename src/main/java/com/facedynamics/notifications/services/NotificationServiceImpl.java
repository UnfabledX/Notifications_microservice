package com.facedynamics.notifications.services;

import com.facedynamics.notifications.controllers.UserEventService;
import com.facedynamics.notifications.handler.NotFoundException;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.facedynamics.notifications.model.NotificationType.*;
import static com.facedynamics.notifications.utils.Constants.*;

/**
 * Service class that is responsible for business logic of
 * notification processing.
 *
 * @author osynelnyk
 */
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserEventService userEventService;

    private final EmailService emailService;

    private CommandFactory factory;

    /**
     * Finds all notifications of specified user ID.
     *
     * @param ownerId ID of the user who owns his notifications
     * @return the list of found notifications or response HTTP.NOT_FOUND
     * if ID of the user is not found in the database.
     */
    @Override
    public List<Notification> getAllNotificationsByUserId(int page, int ownerId) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if (notificationRepository.existsByOwnerId(ownerId)) {
            Page<Notification> notifications = notificationRepository.findAllByOwnerId(ownerId, pageable);
            return notifications.getContent();
        } else {
            throw new NotFoundException(USER_IS_NOT_FOUND, ownerId);
        }
    }

    /**
     * Deletes all notifications of specified user ID.
     *
     * @param ownerId ID of the user who owns his notifications
     */
    @Override
    public void deleteAllNotificationsByOwnerId(int ownerId) {
        if (notificationRepository.existsByOwnerId(ownerId)) {
            notificationRepository.deleteNotificationsByOwnerId(ownerId);
        } else {
            throw new NotFoundException(USER_IS_NOT_FOUND, ownerId);
        }
    }

    /**
     * Deletes the concrete notification of specified ID.
     *
     * @param Id Notification ID which must be deleted
     * @return ID of the deleted notification if operation is successful
     * or response HTTP.NOT_FOUND if ID of the notification is not
     * found in the database.
     */
    @Override
    public Long deleteNotificationById(long Id) {
        Optional<Notification> notification = notificationRepository.findNotificationById(Id);
        if (notification.isEmpty()) {
            throw new NotFoundException(ID_OF_THE_NOTIFICATION_IS_NOT_FOUND, Id);
        }
        notificationRepository.deleteNotificationById(Id);
        return notification.get().getId();
    }

    /**
     * Creates notification of specified type, saves it to database,
     * sends an email letter to recipient user (who received a notification)
     * @param receivedDTO dto with incoming necessary data
     * @return returns a response body of a saved notification
     */
    @Override
    public NotificationResponseDTO createNotification(NotificationDto receivedDTO) {
        NotificationContent.Type type = receivedDTO.content().getType();
        if (factory == null) {
            factory = new CommandFactory(userEventService, emailService, notificationRepository);
        }
        return factory.createCommand(type).execute(receivedDTO);
    }
}
