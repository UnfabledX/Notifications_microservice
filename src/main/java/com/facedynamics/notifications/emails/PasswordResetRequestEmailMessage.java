package com.facedynamics.notifications.emails;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class PasswordResetRequestEmailMessage extends EmailMessage {

    public static final String NEW_RESET_PASSWORD = "Request to Reset Password!";

    @Value("${source.mail.template.password-reset-request}")
    private String emailTemplate;

    @Override
    public StringWriter getLetterBody() {
        //todo
        return null;
    }

    @Override
    public String getLetterSubject(){
        return NEW_RESET_PASSWORD;
    }
}
