package com.facedynamics.notifications.services;

import com.facedynamics.notifications.emails.EmailComposer;
import com.facedynamics.notifications.model.NotificationUserServiceDTO;
import com.facedynamics.notifications.model.dto.NotificationDto;
import com.facedynamics.notifications.model.dto.ContentWithEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.StringWriter;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${source.mail.address}")
    protected String emailFrom;

    private final JavaMailSender mailSender;

    private final EmailComposer emailComposer;

    @Override
    public void sendEmail(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO,
                          String triggerUserName) {
        StringWriter writer = emailComposer.getWriter(receivedDTO, ownerDTO, triggerUserName);

        MimeMessagePreparator prep = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(getEmail(receivedDTO, ownerDTO));
            helper.setFrom(emailFrom);
            helper.setSubject(emailComposer.getSubject(receivedDTO));
            helper.setText(writer.toString(), true);
        };
        mailSender.send(prep);
    }

    private static String getEmail(NotificationDto receivedDTO, NotificationUserServiceDTO ownerDTO) {
        if (ownerDTO == null) {
            ContentWithEmail withEmail = (ContentWithEmail) receivedDTO.content();
            return withEmail.getEmail();
        } else {
            return ownerDTO.getEmail();
        }
    }
}
