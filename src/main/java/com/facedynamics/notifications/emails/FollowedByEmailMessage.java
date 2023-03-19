package com.facedynamics.notifications.emails;

import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component("FOLLOWED_BY")
public class FollowedByEmailMessage extends EmailMessage {

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
