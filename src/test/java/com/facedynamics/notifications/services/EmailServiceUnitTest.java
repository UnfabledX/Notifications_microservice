package com.facedynamics.notifications.services;

import com.facedynamics.BaseTest;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.CommentReplied;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.PostCommented;
import com.facedynamics.notifications.model.dto.UserRegistered;
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
    public void sendPostCommentedTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 5, 12, 12);
        NotificationDto getDTO = new NotificationDto(321L, 123L,
                new PostCommented(4L, 3L, "some post...", "some comment"), dateTime, null);
        NotificationUserServiceDTO userServiceDTO321 = NotificationUserServiceDTO.builder()
                .ownerName("Oleksii")
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

    @Test
    public void sendCommentRepliedTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 5, 12, 12);
        NotificationDto getDTO = new NotificationDto(321L, 123L,
                new CommentReplied(4L, 3L, "some comment", "some reply"), dateTime, null);
        NotificationUserServiceDTO userServiceDTO321 = NotificationUserServiceDTO.builder()
                .ownerName("Oleksii")
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
        Assertions.assertTrue(context.contains("Hello Oleksii, you have a new reply to your comment!"));
        Assertions.assertTrue(context.contains("Dragon has replied <i>\"some reply ...\" </i>"));
        Assertions.assertTrue(context.contains("on your comment <i>\"some comment ...\" </i><br/>"));
        Assertions.assertTrue(context.contains("at 2019-12-05T12:12"));
    }

    @Test
    public void sendUserRegisteredTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2019, 12, 5, 12, 12);
        NotificationDto getDTO = new NotificationDto(321L, null,
                new UserRegistered("Oleksii", "some_mail@gmail.com", "some link", 15), dateTime, null);

        service.sendEmail(getDTO, null, null);

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
        Assertions.assertTrue(context.contains("Hello Oleksii, you just registered your account with the email:"));
        Assertions.assertTrue(context.contains("some_mail@gmail.com <br/>"));
        Assertions.assertTrue(context.contains("at 2019-12-05T12:12</h3>"));
        Assertions.assertTrue(context.contains("One last step, just click here on a <a href=\"some link\">Registration Link</a><br/>"));
        Assertions.assertTrue(context.contains("Please note, the above link is only valid for 15 minutes."));
    }
}
