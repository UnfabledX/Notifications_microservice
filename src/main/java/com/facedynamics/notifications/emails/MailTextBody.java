package com.facedynamics.notifications.emails;

import java.io.StringWriter;

public interface MailTextBody {

    StringWriter getLetterBody();

    String getLetterSubject();
}
