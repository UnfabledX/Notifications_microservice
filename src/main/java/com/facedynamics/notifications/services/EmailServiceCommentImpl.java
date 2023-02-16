package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.dto.NotificationDetails;
import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceCommentImpl extends EmailService{

    @Override
    public void sendEmail(NotificationGetDTO getDTO) {
        NotificationDetails details = getDTO.getDetails();
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(details.getUserEmail());
            helper.setFrom(super.emailFrom);
            Map model = new HashMap();
            //model.put();
        };
        this.mailSender.send(preparator);
    }
}
