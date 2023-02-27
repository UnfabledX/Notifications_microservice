package com.facedynamics.notifications;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.model.Notification;
import com.facedynamics.notifications.utils.ErrorMessage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static com.facedynamics.notifications.utils.Constants.PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests extends BaseTest {

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
    public void getAllNotificationsByUserIdTest_validIdAndPage() {
        userId = 4;
        int pageNumber = 0;
        ResponseEntity<List> response = template.getForEntity(
                createURLWithPort() + "/users/{userId}?page={pageNumber}",
                List.class, userId, pageNumber);
        List<Notification> notifications = response.getBody();
        if (Optional.ofNullable(notifications).isPresent()) {
            assertEquals(PAGE_SIZE, notifications.size());
        }
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void getAllNotificationsByUserIdTest_notValidId() {
        userId = 4321;
        int pageNumber = 0;
        ResponseEntity<ErrorMessage> response = template.exchange(
                createURLWithPort() + "/users/{userId}?page={pageNumber}", HttpMethod.GET,
                null, ErrorMessage.class, userId, pageNumber);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void getAllNotificationsByUserIdTest_notValidInput() {
        userId = 4;
        ResponseEntity<ErrorMessage> response = template.exchange(
                createURLWithPort() + "/users/{userId}?page={pageNumber}", HttpMethod.GET,
                null, ErrorMessage.class, userId, "abc");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(4)
    @Sql(statements = "INSERT INTO notification_db.notifications VALUES " +
            "(DEFAULT, 3, 8, 4, '2023-01-12 13:47:36')," +
            "(DEFAULT, 3, 10, 1, '2022-06-23 13:47:36')",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAllNotificationsByUserIdTest_validId() {
        userId = 3;
        ResponseEntity<String> response = template.exchange(createURLWithPort() + "/users/{userId}",
                HttpMethod.DELETE, null, String.class, userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void deleteAllNotificationsByUserIdTest_IdIsNotPresent() {
        userId = 432100;
        ResponseEntity<ErrorMessage> response = template.exchange(createURLWithPort() + "/users/{userId}",
                HttpMethod.DELETE, null, ErrorMessage.class, userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    @Sql(statements = "INSERT INTO notification_db.notifications VALUES " +
            "(7, 4, 20, 1, '2022-08-23 08:22:07')",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteNotificationByIdTest_idIsPresent() {
        long notificationId = 7;
        ResponseEntity<Long> response = template.exchange(createURLWithPort() + "/{notificationId}",
                HttpMethod.DELETE, null, Long.class, notificationId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificationId, response.getBody());
    }

    @Test
    @Order(7)
    public void deleteNotificationByIdTest_idIsNotPresent() {
        long notificationId = 5321;
        ResponseEntity<ErrorMessage> response = template.exchange(createURLWithPort() + "/{notificationId}",
                HttpMethod.DELETE, null, ErrorMessage.class, notificationId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createNotificationCommentTest() {
        
    }
}
