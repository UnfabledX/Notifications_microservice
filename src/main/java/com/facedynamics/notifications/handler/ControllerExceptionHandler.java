package com.facedynamics.notifications.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private final static String PROBLEMS = "problemDetails";

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Data is not found");
        pd.setProperty(PROBLEMS, Error.builder().message(e.getMessage()).wrongValue(e.getWrongValue()).build());
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Failed validation");
        FieldError fieldError = e.getBindingResult().getFieldErrors().iterator().next();
        Error error = Error.builder()
                .message(fieldError.getDefaultMessage())
                .field(fieldError.getField())
                .wrongValue(fieldError.getRejectedValue().toString()).build();
        pd.setProperty(PROBLEMS, error);
        return pd;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Values can not be read");
        String wrongValue = StringUtils.substringBetween(e.getMessage(), "\"", "\"");
        pd.setProperty(PROBLEMS, Error.builder().message(e.getMessage()).wrongValue(wrongValue).build());
        return pd;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Wrong input parameter");
        String actualField = e.getName();
        Object wrongValue = Optional.ofNullable(e.getValue()).orElse("");
        String requiredType = e.getRequiredType().getSimpleName();
        String message = String.format("The field '%s' must have a valid type of '%s'", actualField, requiredType);
        pd.setProperty(PROBLEMS, Error.builder().message(message).field(actualField).wrongValue(wrongValue.toString()).build());
        return pd;
    }

    @ExceptionHandler(WrongEnumException.class)
    public ProblemDetail handleIllegalArgumentException(WrongEnumException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Wrong ENUM type");
        pd.setProperty(PROBLEMS, Error.builder().message(e.getMessage()).wrongValue(e.getEnumName()).build());
        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Constraint violation");
        ConstraintViolation<?> cv = e.getConstraintViolations().iterator().next();
        String field = cv.getPropertyPath().toString().substring(cv.getPropertyPath().toString().lastIndexOf('.') + 1);
        String value = cv.getInvalidValue().toString();
        String message = String.format(cv.getMessage(), field);
        Error error = Error.builder().message(message).field(field).wrongValue(value).build();
        problemDetail.setProperty(PROBLEMS, error);
        return problemDetail;
    }
}
