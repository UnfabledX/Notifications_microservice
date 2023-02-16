package com.facedynamics.notifications.services;

import com.facedynamics.notifications.model.dto.NotificationGetDTO;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
abstract class EmailService {

    @Value("${source.mail.address}")
    String emailFrom;

    JavaMailSender mailSender;

    private VelocityEngine engine;

    abstract void sendEmail(NotificationGetDTO getDTO);

}
