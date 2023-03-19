package com.facedynamics.notifications.emails;

import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class UserRegisteredEmailMessage extends EmailMessage {

    public static final String NEW_REGISTRATION = "Your registration for FaceDynamics social network!";

    @Override
    public StringWriter getLetterBody() {
        //todo
        return null;
    }

    @Override
    public String getLetterSubject(){
        return NEW_REGISTRATION;
    }
}
