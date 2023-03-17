package com.facedynamics.notifications.emails;

import java.io.StringWriter;

public class FollowEmailMessage extends EmailMessage {

    public static final String NEW_FOLLOW = "You have a NEW Follow!";

    @Override
    public StringWriter getLetterBody() {
        //todo
        return null;
    }

    @Override
    public String getLetterSubject(){
        return NEW_FOLLOW;
    }
}
