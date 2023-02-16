package com.facedynamics.notifications.controllers;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationType;
import com.facedynamics.notifications.model.dto.NotificationDetails;
import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationReturnDTO;
import com.facedynamics.notifications.services.NotificationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
public class NotificationControllerUnitTest extends BaseTest {

    public static final String NOTIFICATIONS_BY_USER_ID = "/notifications/users/{userId}";

    public static final String NOTIFICATIONS_NOTIFICATION_ID = "/notifications/{notificationId}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationServiceImpl service;

    private List<Notification> list;

    private int ownerId;

    @BeforeEach
    public void init() {
        NotificationType type = NotificationType.FOLLOW;
        Notification n1 = Notification.builder()
                .id(1L)
                .ownerId(3)
                .triggererId(5)
                .notificationType(type.getId())
                .createdAt(LocalDateTime.of(2022, 12, 1, 16, 36, 54))
                .build();
        Notification n2 = Notification.builder()
                .id(2L)
                .ownerId(3)
                .triggererId(6)
                .notificationType(type.getId())
                .createdAt(LocalDateTime.of(2021, 10, 12, 12, 12, 33))
                .build();
        Notification n3 = Notification.builder()
                .id(5L)
                .ownerId(3)
                .triggererId(7)
                .notificationType(type.getId())
                .createdAt(LocalDateTime.of(2019, 10, 14, 12, 12, 33))
                .build();
        list = Arrays.asList(n1, n2, n3);
    }

    @Test
    public void getNotificationsByUserIdTest_ownerIdIsPresent() throws Exception {
        ownerId = 3;
        Mockito.when(service.getAllNotificationsByUserId(0, ownerId)).thenReturn(list);

        mockMvc.perform(get(NOTIFICATIONS_BY_USER_ID, ownerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[1].triggererId", Matchers.is(6)));
        Mockito.verify(service, times(1))
                .getAllNotificationsByUserId(0, ownerId);
    }

    @Test
    public void getNotificationsByUserIdTest_IdIsNotPresent() throws Exception {
        ownerId = 12212;
        Mockito.when(service.getAllNotificationsByUserId(1, ownerId))
                .thenThrow(ValidationException.class);

        mockMvc.perform(get(NOTIFICATIONS_BY_USER_ID + "?page={page}", ownerId, 1))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        Mockito.verify(service, times(1))
                .getAllNotificationsByUserId(1, ownerId);
    }

    @Test
    public void deleteNotificationsByUserIdTest_IdIsNotPresent() throws Exception {
        ownerId = 54321;
        Mockito.doThrow(ValidationException.class)
                .when(service).deleteAllNotificationsByOwnerId(ownerId);

        mockMvc.perform(delete(NOTIFICATIONS_BY_USER_ID, ownerId))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(service, times(1))
                .deleteAllNotificationsByOwnerId(ownerId);
    }

    @Test
    public void deleteNotificationsByUserIdTest_IdIsPresent() throws Exception {
        ownerId = 7;
        Mockito.doNothing().when(service)
                .deleteAllNotificationsByOwnerId(ownerId);

        mockMvc.perform(delete(NOTIFICATIONS_BY_USER_ID, ownerId))
                .andExpect(status().isOk());
        Mockito.verify(service, times(1))
                .deleteAllNotificationsByOwnerId(ownerId);
    }

    @Test
    public void deleteNotificationByIDTest_IdIsNotPresent() throws Exception {
        long notificationId = 1000;
        Mockito.when(service.deleteNotificationById(notificationId))
                .thenThrow(ValidationException.class);

        mockMvc.perform(delete(NOTIFICATIONS_NOTIFICATION_ID, notificationId))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(service, times(1))
                .deleteNotificationById(notificationId);
    }

    @Test
    public void deleteNotificationByIDTest_IdIsPresent() throws Exception {
        long notificationId = 2;
        Mockito.when(service.deleteNotificationById(notificationId))
                .thenReturn(notificationId);

        mockMvc.perform(delete(NOTIFICATIONS_NOTIFICATION_ID, notificationId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(notificationId)));
        Mockito.verify(service, times(1))
                .deleteNotificationById(notificationId);
    }

    @Test
    public void createNotificationCommentTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 5, 12, 12);
        NotificationDetails details = NotificationDetails.builder()
                .userId(123)
                .postText("some post...")
                .commentText("some comment")
                .createdAt(dateTime).build();
        NotificationGetDTO getDTO = new NotificationGetDTO(321, "comment", details);
        Mockito.when(service.createNotification(getDTO))
                .thenReturn(new NotificationReturnDTO("Dragon", NotificationType.COMMENT, dateTime));

        mockMvc.perform(post("/notifications").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.aMapWithSize(3)))
                .andExpect(jsonPath("$.type", Matchers.is("COMMENT")));

        verify(service, times(1)).createNotification(getDTO);
    }
}
