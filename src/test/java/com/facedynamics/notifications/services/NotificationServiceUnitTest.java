package com.facedynamics.notifications.services;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationType;
import com.facedynamics.notifications.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.facedynamics.notifications.util.Constants.PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class NotificationServiceUnitTest extends BaseTest {

    private final NotificationRepository repository = mock(NotificationRepository.class);

    @InjectMocks
    private NotificationServiceImpl service;

    private List<Notification> notificationList;

    private Page<Notification> resultList;

    private int ownerId;

    @BeforeEach
    public void init() {
        notificationList = new ArrayList<>();
        NotificationType type = new NotificationType(2L, "Follow", "Some text...");
        Notification n1 = Notification.builder()
                .id(1L)
                .ownerId(3)
                .triggererId(5)
                .notificationTypeId(type)
                .createdAt(LocalDateTime.of(2022, 12, 1, 16, 36, 54))
                .build();
        Notification n2 = Notification.builder()
                .id(2L)
                .ownerId(3)
                .triggererId(4)
                .notificationTypeId(type)
                .createdAt(LocalDateTime.of(2021, 10, 12, 12, 12, 33))
                .build();
        notificationList.addAll(Arrays.asList(n1, n2));
        resultList = new PageImpl<>(notificationList);
    }

    @Test
    void getAllNotificationsByUserIdTest_validId() {
        ownerId = 3;
        when(repository.findAllByOwnerId(ownerId, PageRequest.of(0, PAGE_SIZE))).thenReturn(resultList);
        when(repository.existsByOwnerId(ownerId)).thenReturn(true);
        List<Notification> actualList = service.getAllNotificationsByUserId(0, ownerId);
        assertNotNull(actualList);
        assertEquals(notificationList, actualList);
    }

    @Test
    public void getAllNotificationsByUserIdTest_notValidId() {
        ownerId = 12345;
        when(repository.findAllByOwnerId(ownerId, PageRequest.of(0, PAGE_SIZE)))
                .thenThrow(ValidationException.class);
        assertThrows(ValidationException.class,
                () -> service.getAllNotificationsByUserId(0, ownerId));
    }

    @Test
    void deleteAllNotificationsByUserIdTest_notValidId() {
        ownerId = 3210;
        doThrow(ValidationException.class).when(repository).deleteNotificationsByOwnerId(ownerId);
        assertThrows(ValidationException.class,
                () -> service.deleteAllNotificationsByOwnerId(ownerId));
    }

    @Test
    void deleteAllNotificationsByUserIdTest_validId() {
        ownerId = 3;
        doNothing().when(repository).deleteNotificationsByOwnerId(ownerId);
        when(repository.existsByOwnerId(ownerId)).thenReturn(true);
        assertDoesNotThrow(() -> service.deleteAllNotificationsByOwnerId(ownerId));
    }

    @Test
    void deleteNotificationByIdTest_notValidId() {
        long notificationId = 3000;
        doAnswer(invocation -> null).when(repository).deleteNotificationById(anyLong());
        when(repository.findNotificationById(eq(notificationId)))
                .thenReturn(Optional.empty());
        assertThrows(ValidationException.class,
                () -> service.deleteNotificationById(notificationId));
    }

    @Test
    void deleteNotificationByIdTest_validId() {
        long notificationId = 1;
        when(repository.findNotificationById(notificationId))
                .thenReturn(Optional.ofNullable(notificationList.get(0)));
        assertEquals(1L, service.deleteNotificationById(notificationId));
    }
}