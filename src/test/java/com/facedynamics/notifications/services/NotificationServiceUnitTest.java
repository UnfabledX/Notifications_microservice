package com.facedynamics.notifications.services;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.controllers.UserEventService;
import com.facedynamics.notifications.handler.NotFoundException;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PostCommented;
import com.facedynamics.notifications.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.facedynamics.notifications.model.dto.NotificationContent.Type.FOLLOWED_BY;
import static com.facedynamics.notifications.model.dto.NotificationContent.Type.POST_COMMENTED;
import static com.facedynamics.notifications.utils.Constants.PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class NotificationServiceUnitTest extends BaseTest {

    private final NotificationRepository repository = mock(NotificationRepository.class);

    @InjectMocks
    private NotificationServiceImpl service;

    @Mock
    private EmailServiceImpl emailService;

    @Mock
    private UserEventService userEventService;

    private List<Notification> notificationList;

    private Page<Notification> resultList;

    private int ownerId;

    @BeforeEach
    public void init() {
        notificationList = new ArrayList<>();
        Notification n1 = Notification.builder()
                .id(1L)
                .ownerId(3L)
                .createdById(5L)
                .details(NotificationDetails.builder()
                        .id(1L)
                        .type(POST_COMMENTED.name())
                        .postId(33L)
                        .commentId(12L)
                        .build())
                .createdAt(LocalDateTime.of(2022, 12, 1, 16, 36, 54))
                .build();
        Notification n2 = Notification.builder()
                .id(2L)
                .ownerId(3L)
                .createdById(4L)
                .details(NotificationDetails.builder()
                        .id(2L)
                        .type(FOLLOWED_BY.name())
                        .postId(33L)
                        .commentId(13L)
                        .build())
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
                .thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class,
                () -> service.getAllNotificationsByUserId(0, ownerId));
    }

    @Test
    void deleteAllNotificationsByUserIdTest_notValidId() {
        ownerId = 3210;
        doThrow(NotFoundException.class).when(repository).deleteNotificationsByOwnerId(ownerId);
        assertThrows(NotFoundException.class,
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
        assertThrows(NotFoundException.class,
                () -> service.deleteNotificationById(notificationId));
    }

    @Test
    void deleteNotificationByIdTest_validId() {
        long notificationId = 1;
        when(repository.findNotificationById(notificationId))
                .thenReturn(Optional.ofNullable(notificationList.get(0)));
        assertEquals(1L, service.deleteNotificationById(notificationId));
    }

    @Test
    void createNotificationCommentTest() {
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 5, 12, 12);
        NotificationDto getDTO = new NotificationDto(321L, 123L,
                new PostCommented(4L, 3L, "some post...", "some comment"), dateTime, null);
        NotificationUserServiceDTO userServiceDTO321 = NotificationUserServiceDTO.builder()
                .ownerName("Oleksii")
                .username("Unfabled")
                .email("alex0destroyer@gmail.com")
                .build();
        NotificationUserServiceDTO userServiceDTO123 = NotificationUserServiceDTO.builder()
                .ownerName("Pasha")
                .username("Dragon")
                .email("pasha@gmail.com")
                .build();
        when(userEventService.getUserById(321)).thenReturn(userServiceDTO321);
        when(userEventService.getUserById(123)).thenReturn(userServiceDTO123);
        doNothing().when(emailService)
                .sendEmail(getDTO, userServiceDTO321, userServiceDTO123.getUsername());
        NotificationResponseDTO actualReturnDTO = service.createNotification(getDTO);
        NotificationResponseDTO expected = new NotificationResponseDTO(userServiceDTO123.getUsername(), POST_COMMENTED, dateTime);
        assertEquals(expected, actualReturnDTO);
    }
}