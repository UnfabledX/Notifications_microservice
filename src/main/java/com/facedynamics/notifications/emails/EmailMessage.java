package com.facedynamics.notifications.emails;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public abstract class EmailMessage implements MailTextBody{

    protected final VelocityEngine engine;
}
