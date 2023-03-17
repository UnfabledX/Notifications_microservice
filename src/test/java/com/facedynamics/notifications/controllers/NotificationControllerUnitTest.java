package com.facedynamics.notifications.controllers;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.handler.NotFoundException;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.model.NotificationDetails;
import com.facedynamics.notifications.model.NotificationResponseDTO;
import com.facedynamics.notifications.model.dto.NotificationContent;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PostCommented;
import com.facedynamics.notifications.services.NotificationServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.facedynamics.notifications.controllers.NotificationController.NOTIFICATION;
import static com.facedynamics.notifications.model.dto.NotificationContent.Type.POST_COMMENTED;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
public class NotificationControllerUnitTest extends BaseTest {

    public static final String NOTIFICATIONS_BY_USER_ID = NOTIFICATION + "/users/{userId}";

    public static final String NOTIFICATIONS_NOTIFICATION_ID = NOTIFICATION + "/{notificationId}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationServiceImpl service;

    private Page<Notification> pageList;

    private Long ownerId;

    private  Pageable pageable;


    @BeforeEach
    public void init() {
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
                .createdById(6L)
                .details(NotificationDetails.builder()
                        .id(2L)
                        .type(POST_COMMENTED.name())
                        .postId(33L)
                        .commentId(13L)
                        .build())
                .createdAt(LocalDateTime.of(2021, 10, 12, 12, 12, 33))
                .build();
        Notification n3 = Notification.builder()
                .id(3L)
                .ownerId(3L)
                .createdById(7L)
                .details(NotificationDetails.builder()
                        .id(3L)
                        .type(POST_COMMENTED.name())
                        .postId(33L)
                        .commentId(14L)
                        .build())
                .createdAt(LocalDateTime.of(2019, 10, 14, 12, 12, 33))
                .build();
        List<Notification> list = Arrays.asList(n1, n2, n3);
        pageList = new PageImpl<>(list);
        pageable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
    }

    @Test
    public void getNotificationsByUserIdTest_ownerIdIsPresent() throws Exception {
        ownerId = 3L;
        Mockito.when(service.getAllNotificationsByUserId(ownerId, pageable)).thenReturn(pageList);

        mockMvc.perform(get(NOTIFICATIONS_BY_USER_ID, ownerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notifications", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.notifications[1].createdById", Matchers.is(6)));
        Mockito.verify(service, times(1))
                .getAllNotificationsByUserId(ownerId, pageable);
    }

    @Test
    public void getNotificationsByUserIdTest_IdIsNotPresent() throws Exception {
        ownerId = 12212L;
        pageable = PageRequest.of(1, 5, Sort.by("createdAt").descending());
        Mockito.when(service.getAllNotificationsByUserId(ownerId, pageable))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(get(NOTIFICATIONS_BY_USER_ID + "?page={page}", ownerId, 1))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE));
        Mockito.verify(service, times(1))
                .getAllNotificationsByUserId(ownerId, pageable);
    }

    @Test
    public void deleteNotificationsByUserIdTest_IdIsNotPresent() throws Exception {
        ownerId = 54321L;
        Mockito.doThrow(NotFoundException.class)
                .when(service).deleteAllNotificationsByOwnerId(ownerId);

        mockMvc.perform(delete(NOTIFICATIONS_BY_USER_ID, ownerId))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
        Mockito.verify(service, times(1))
                .deleteAllNotificationsByOwnerId(ownerId);
    }

    @Test
    public void deleteNotificationsByUserIdTest_IdIsPresent() throws Exception {
        ownerId = 7L;
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
                .thenThrow(NotFoundException.class);

        mockMvc.perform(delete(NOTIFICATIONS_NOTIFICATION_ID, notificationId))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
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
        NotificationContent content = new PostCommented(4L, 3L, "some post...", "some comment");
        NotificationDto getDTO = new NotificationDto(321L, 123L, content, dateTime, null);

        NotificationResponseDTO dto = new NotificationResponseDTO("Dragon", POST_COMMENTED, dateTime);
        Mockito.when(service.createNotification(eq(getDTO)))
                .thenReturn(dto);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = objectMapper.writeValueAsString(getDTO);
        mockMvc.perform(post(NOTIFICATION)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.aMapWithSize(3)))
                .andExpect(jsonPath("$.type", Matchers.is("POST_COMMENTED")));

        verify(service, times(1)).createNotification(getDTO);
    }
}
