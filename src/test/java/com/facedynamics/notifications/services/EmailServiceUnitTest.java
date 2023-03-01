package com.facedynamics.notifications.services;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.model.dto.NotificationDetails;
import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import com.facedynamics.notifications.model.dto.NotificationUserServiceDTO;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@RequiredArgsConstructor
public class EmailServiceUnitTest extends BaseTest {

    @Mock
    private JavaMailSender mailSender;

    private EmailServiceImpl service;

    @BeforeEach
    public void init() {
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        service = new EmailServiceImpl(mailSender);
    }

    @Test
    public void sendCommentEmailTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 5, 12, 12);
        NotificationDetails details = NotificationDetails.builder()
                .userId(123)
                .postText("some post...")
                .commentText("some comment")
                .createdAt(dateTime).build();
        NotificationGetDTO getDTO = new NotificationGetDTO(321, "comment", details);

        NotificationUserServiceDTO userServiceDTO321 = NotificationUserServiceDTO.builder()
                .name("Oleksii")
                .username("Unfabled")
                .email("alex0destroyer@gmail.com")
                .build();
        NotificationUserServiceDTO userServiceDTO123 = NotificationUserServiceDTO.builder()
                .username("Dragon")
                .build();

        service.sendEmail(getDTO, userServiceDTO321, userServiceDTO123.getUsername());

        ArgumentCaptor<MimeMessagePreparator> captor = ArgumentCaptor.forClass(MimeMessagePreparator.class);
        verify(mailSender).send(captor.capture());
        MimeMessagePreparator pr = captor.getValue();
        Assertions.assertNotNull(pr);

        Field[] fields = pr.getClass().getDeclaredFields();
        String context = "";
        for (Field f: fields) {
            f.setAccessible(true);
            Object obj = f.get(pr);
            if (obj instanceof StringWriter){
                context = obj.toString();
            }
        }
        Assertions.assertTrue(context.contains("Hello Oleksii, you have a new comment!"));
        Assertions.assertTrue(context.contains("Dragon has left the comment <i>\"some comment ...\" </i>"));
        Assertions.assertTrue(context.contains("on your post <i>\"some post... ...\" </i><br/>"));
        Assertions.assertTrue(context.contains("at 2019-12-05T12:12"));
    }
}
