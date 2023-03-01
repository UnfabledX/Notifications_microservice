package com.facedynamics.notifications.handler;

public class WrongEnumException extends RuntimeException {
    private final String enumName;

    public WrongEnumException(String message, String name) {
        super(message);
        enumName = name;
    }

    public String getEnumName() {
        return enumName;
    }
}
