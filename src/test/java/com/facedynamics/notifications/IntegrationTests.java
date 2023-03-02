package com.facedynamics.notifications;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.handler.Error;
import com.facedynamics.notifications.model.NotificationType;
import com.facedynamics.notifications.model.dto.NotificationDetails;
import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationResponseDTO;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests extends BaseTest {

    public final static int PAGE_SIZE_DEFAULT = 5;

    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    private int port;

    private int userId;

    private String createURLWithPort() {
        return "http://localhost:" + port + "/notifications";
    }

    @Test
    @Order(1)
    public void getAllNotificationsByUserIdTest_validId() {
        userId = 4;
        int pageNumber = 0;
        ResponseEntity<Map> response = template.getForEntity(
                createURLWithPort() + "/users/{userId}?page={pageNumber}",
                Map.class, userId, pageNumber);
        Map<String, Object> notifications = response.getBody();
        assertNotNull(notifications);
        assertEquals(PAGE_SIZE_DEFAULT, notifications.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void getAllNotificationsByUserIdTest_notValidId() {
        userId = 4321;
        int pageNumber = 0;
        ResponseEntity<Error> response = template.exchange(
                createURLWithPort() + "/users/{userId}?page={pageNumber}", HttpMethod.GET,
                null, Error.class, userId, pageNumber);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void getAllNotificationsByUserIdTest_notValidInput() {
        userId = 4;
        ResponseEntity<Map> response = template.exchange(
                createURLWithPort() + "/users/{userId}?page={pageNumber}", HttpMethod.GET,
                null, Map.class, userId, "abc");
        Map<String, Object> notifications = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(notifications);
        assertEquals(PAGE_SIZE_DEFAULT, notifications.size());
    }

    @Test
    @Order(4)
    @Sql(statements = {details1, notific1}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteAllNotificationsByUserIdTest_validId() {
        userId = 10;
        ResponseEntity<String> response = template.exchange(createURLWithPort() + "/users/{userId}",
                HttpMethod.DELETE, null, String.class, userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void deleteAllNotificationsByUserIdTest_IdIsNotPresent() {
        userId = 432100;
        ResponseEntity<Error> response = template.exchange(createURLWithPort() + "/users/{userId}",
                HttpMethod.DELETE, null, Error.class, userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    @Sql(statements = {details2, notific2}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteNotificationByIdTest_idIsPresent() {
        long notificationId = 11;
        ResponseEntity<Long> response = template.exchange(createURLWithPort() + "/{notificationId}",
                HttpMethod.DELETE, null, Long.class, notificationId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificationId, response.getBody());
    }

    @Test
    @Order(7)
    public void deleteNotificationByIdTest_idIsNotPresent() {
        long notificationId = 5321;
        ResponseEntity<Error> response = template.exchange(createURLWithPort() + "/{notificationId}",
                HttpMethod.DELETE, null, Error.class, notificationId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createNotificationCommentTest_OK() {
        NotificationGetDTO getDTO = new NotificationGetDTO(321, "comment", NotificationDetails.builder()
                .userId(123)
                .postText("Some post")
                .commentText("Some comment")
                .createdAt(LocalDateTime.parse("2021-12-03T10:15:30"))
                .build());
        ResponseEntity<NotificationResponseDTO> response = template.postForEntity(createURLWithPort(),
                getDTO, NotificationResponseDTO.class);
        NotificationResponseDTO responseDTO = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseDTO);
        assertEquals(NotificationType.COMMENT, responseDTO.getType());
    }

    @Test
    public void createNotificationCommentTest_InvalidNotificationType() {
        NotificationGetDTO getDTO = new NotificationGetDTO(321, "wrong type", NotificationDetails.builder()
                .userId(123)
                .postText("Some post")
                .commentText("Some comment")
                .createdAt(LocalDateTime.parse("2021-12-03T10:15:30"))
                .build());
        ResponseEntity<ProblemDetail> response = template.postForEntity(createURLWithPort(),
                getDTO, ProblemDetail.class);
        ProblemDetail detail = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(detail);
        assertNotNull(detail.getProperties());

        Map<String, Object> map = detail.getProperties();
        LinkedHashMap<?,?> innerData = (LinkedHashMap<?,?>) map.get("problem details");
        String actual = innerData.get("wrongValue").toString();
        assertEquals("wrong type", actual);
    }

    @Test
    public void createNotificationCommentTest_InvalidUserId() {
        NotificationGetDTO getDTO = new NotificationGetDTO(321, "wrong type", NotificationDetails.builder()
                .userId(-123)
                .postText("Some post")
                .commentText("Some comment")
                .createdAt(LocalDateTime.parse("2021-12-03T10:15:30"))
                .build());
        ResponseEntity<ProblemDetail> response = template.postForEntity(createURLWithPort(),
                getDTO, ProblemDetail.class);
        ProblemDetail detail = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(detail);
        assertNotNull(detail.getProperties());

        Map<String, Object> map = detail.getProperties();
        LinkedHashMap<?,?> innerData = (LinkedHashMap<?,?>) map.get("problem details");
        String actual = innerData.get("wrongValue").toString();
        assertEquals("-123", actual);
    }
}
