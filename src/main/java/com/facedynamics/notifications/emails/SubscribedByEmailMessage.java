package com.facedynamics.notifications.emails;

import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class SubscribedByEmailMessage extends EmailMessage {

    public static final String NEW_SUBSCRIPTION = "You have a NEW Subscription!";

    @Override
    public StringWriter getLetterBody() {
        //todo
        return null;
    }

    @Override
    public String getLetterSubject(){
        return NEW_SUBSCRIPTION;
    }
}
