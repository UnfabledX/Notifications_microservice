package com.facedynamics.notifications.emails;

import java.io.StringWriter;

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
