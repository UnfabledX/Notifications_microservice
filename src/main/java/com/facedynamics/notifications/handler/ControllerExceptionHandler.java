package com.facedynamics.notifications.handler;

import com.facedynamics.notifications.utils.Error;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private final static String PROBLEMS = "problem details";

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleValidationException(NotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Data is not found");
        pd.setProperty(PROBLEMS, new Error(e.getMessage()));
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Failed validation");
        List<Error> errors = new ArrayList<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(x -> errors.add(new Error(x.getField(), x.getDefaultMessage())));
        pd.setProperty(PROBLEMS, errors.get(0));
        return pd;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Values can not be read");
        pd.setProperty(PROBLEMS, new Error(e.getMessage()));
        return pd;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Wrong input parameter");
        String actualField = e.getName();
        String wrongValue = e.getValue() != null ? e.getValue().toString() : "";
        String requiredType = e.getRequiredType().getSimpleName();
        String message = String.format("The field '%s' must have a valid type of '%s'", actualField, requiredType);
        pd.setProperty(PROBLEMS, new Error(actualField, message, wrongValue));
        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Illegal argument");
        pd.setProperty(PROBLEMS, new Error(e.getMessage()));
        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Validation problem");
        List<Error> errors = new ArrayList<>();
        for (ConstraintViolation<?> v : e.getConstraintViolations()) {
            String field = v.getPropertyPath().toString().substring(v.getPropertyPath().toString().lastIndexOf('.') + 1);
            String value = v.getInvalidValue().toString();
            String message = String.format(v.getMessage(), field);
            Error error = new Error(field, message, value);
            errors.add(error);
        }
        problemDetail.setProperty(PROBLEMS, errors.get(0));
        return problemDetail;
    }
}
