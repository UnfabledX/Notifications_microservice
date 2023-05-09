package com.facedynamics.notifications.services;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.clients.UserApiClient;
import com.facedynamics.notifications.exception.NotFoundException;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.dto.NotificationUserServiceDTO;
import com.facedynamics.notifications.dto.NotificationDto;
import com.facedynamics.notifications.dto.PostCommented;
import com.facedynamics.notifications.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.facedynamics.notifications.dto.NotificationContent.Type.POST_COMMENTED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class NotificationServiceUnitTest extends BaseTest {

    public final static int PAGE_SIZE_DEFAULT = 5;

    private final NotificationRepository repository = mock(NotificationRepository.class);

    @InjectMocks
    private NotificationServiceImpl service;

    @Mock
    private EmailServiceImpl emailService;

    @Mock
    private UserApiClient userApiClient;

    private List<Notification> notificationList;

    private Page<Notification> resultList;

    private Long ownerId;

    private Pageable pageable;

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
                        .entityCreatedAt(LocalDateTime.of(2022, 12, 1, 16, 36, 54))
                        .build())
                .notificationCreatedAt(LocalDateTime.now())
                .build();
        Notification n2 = Notification.builder()
                .id(2L)
                .ownerId(3L)
                .createdById(4L)
                .details(NotificationDetails.builder()
                        .id(2L)
                        .type(POST_COMMENTED.name())
                        .postId(33L)
                        .commentId(13L)
                        .entityCreatedAt(LocalDateTime.of(2021, 10, 12, 12, 12, 33))
                        .build())
                .notificationCreatedAt(LocalDateTime.now())
                .build();
        notificationList.addAll(Arrays.asList(n1, n2));
        resultList = new PageImpl<>(notificationList);
        pageable = PageRequest.of(0, 5);
    }

    @Test
    void getAllNotificationsByUserIdTest_validId() {
        ownerId = 3L;
        when(repository.findAllByOwnerId(ownerId, PageRequest.of(0, PAGE_SIZE_DEFAULT))).thenReturn(resultList);
        when(repository.existsByOwnerId(ownerId)).thenReturn(true);
        Page<Notification> actualList = service.getAllNotificationsByUserId(ownerId, pageable);
        assertNotNull(actualList);
        assertEquals(resultList, actualList);
    }

    @Test
    public void getAllNotificationsByUserIdTest_notValidId() {
        ownerId = 12345L;
        when(repository.findAllByOwnerId(ownerId, PageRequest.of(0, PAGE_SIZE_DEFAULT)))
                .thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class,
                () -> service.getAllNotificationsByUserId(ownerId, pageable));
    }

    @Test
    void deleteAllNotificationsByUserIdTest_notValidId() {
        ownerId = 3210L;
        doThrow(NotFoundException.class).when(repository).deleteNotificationsByOwnerId(ownerId);
        assertThrows(NotFoundException.class,
                () -> service.deleteAllNotificationsByOwnerId(ownerId));
    }

    @Test
    void deleteAllNotificationsByUserIdTest_validId() {
        ownerId = 3L;
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
        long ownerId = 321L;
        long createdById = 123L;
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 5, 12, 12);
        NotificationDto getDTO = new NotificationDto(ownerId, createdById,
                new PostCommented(4L, 3L, "some post...", "some comment", dateTime));
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
        when(userApiClient.getById(ownerId)).thenReturn(userServiceDTO321);
        when(userApiClient.getById(createdById)).thenReturn(userServiceDTO123);
        doNothing().when(emailService)
                .sendEmail(getDTO, userServiceDTO321, userServiceDTO123.getUsername());
        NotificationDto actualReturnDTO = service.createNotification(getDTO);
        NotificationDto expected = new NotificationDto(ownerId, createdById,
                new PostCommented(4L, 3L, "some post...", "some comment", dateTime));
        assertEquals(expected, actualReturnDTO);
    }
}