package com.facedynamics.notifications.exception;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NotFoundException extends RuntimeException{

    private long value;

    public NotFoundException(String message, long value) {
        super(message);
        this.value = value;
    }

    public String getWrongValue(){
        return String.valueOf(value);
    }
}
