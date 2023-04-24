package com.facedynamics.notifications.services;

import com.facedynamics.notifications.exception.NotFoundException;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.dto.NotificationContent;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.repository.NotificationRepository;
import com.facedynamics.notifications.services.commands.NotificationProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

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

    public static final String USER_IS_NOT_FOUND = "ID of the user is not found.";
    public static final String ID_OF_THE_NOTIFICATION_IS_NOT_FOUND = "ID of the notification is not found.";

    private final NotificationRepository notificationRepository;
    private final Map<NotificationContent.Type, NotificationProcessor> processor;

    /**
     * Finds all notifications of specified user ID.
     *
     * @param ownerId ID of the user who owns his notifications
     * @return the list of found notifications or response HTTP.NOT_FOUND
     * if ID of the user is not found in the database.
     */
    @Override
    public Page<Notification> getAllNotificationsByUserId(Long ownerId, Pageable pageable) {
        if (notificationRepository.existsByOwnerId(ownerId)) {
            return notificationRepository.findAllByOwnerId(ownerId, pageable);
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
    public void deleteAllNotificationsByOwnerId(Long ownerId) {
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
    public Long deleteNotificationById(Long Id) {
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
     *
     * @param receivedDTO dto with incoming necessary data
     * @return returns a response body of a saved notification
     */
    @Override
    public NotificationDto createNotification(NotificationDto receivedDTO) {
        NotificationContent.Type type = receivedDTO.content().getType();
        return processor
                .get(type)
                .process(receivedDTO);
    }
}
