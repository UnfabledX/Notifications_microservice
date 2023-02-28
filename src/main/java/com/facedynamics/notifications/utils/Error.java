package com.facedynamics.notifications.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    private String field;
    private String message;
    private String wrongValue;

    public Error(String field, String message) {
        this.message = message;
        this.field = field;
    }

    public Error(String message) {
        this.message = message;
    }
}
