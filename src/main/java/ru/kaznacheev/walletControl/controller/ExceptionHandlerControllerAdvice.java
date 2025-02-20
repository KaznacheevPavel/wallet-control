package ru.kaznacheev.walletControl.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.walletControl.dto.ValidationViolation;
import ru.kaznacheev.walletControl.dto.ValidationViolationsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationViolationsResponse onConstraintViolationException(ConstraintViolationException e) {
        Map<String, List<String>> violations = new HashMap<>();
        e.getConstraintViolations().forEach(constraintViolation -> {
            String fieldPath = constraintViolation.getPropertyPath().toString();
            String field = fieldPath.substring(fieldPath.lastIndexOf(".") + 1);
            if (violations.containsKey(field)) {
                violations.get(field).add(constraintViolation.getMessage());
            } else {
                List<String> messages = new ArrayList<>();
                messages.add(constraintViolation.getMessage());
                violations.put(field, messages);
            }
        });

        List<ValidationViolation> validationViolations = violations.entrySet().stream().map(entry -> {
            return new ValidationViolation(
                    entry.getKey(), entry.getKey() + " was not validated",
                    entry.getValue());
        }).toList();

        return new ValidationViolationsResponse(validationViolations);
    }

}
