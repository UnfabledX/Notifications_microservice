package com.facedynamics.notifications.emails;

import java.io.StringWriter;

public class PasswordResetEmailMessage extends EmailMessage {

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
