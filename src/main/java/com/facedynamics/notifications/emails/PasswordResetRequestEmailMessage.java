package com.facedynamics.notifications.emails;

import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component("USER_PASSWORD_RESET_REQUEST")
public class PasswordResetRequestEmailMessage extends EmailMessage {

    public static final String NEW_RESET_PASSWORD = "Request to Reset Password!";

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
