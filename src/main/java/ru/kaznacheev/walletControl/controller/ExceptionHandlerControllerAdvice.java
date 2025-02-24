package ru.kaznacheev.walletControl.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kaznacheev.walletControl.dto.response.BaseExceptionDto;
import ru.kaznacheev.walletControl.dto.response.ValidationExceptionDto;
import ru.kaznacheev.walletControl.exception.BaseApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionDto onConstraintViolationException(ConstraintViolationException e) {
        Map<String, List<String>> invalidFields = new HashMap<>();
        e.getConstraintViolations().forEach(constraintViolation -> {
            String fieldPath = constraintViolation.getPropertyPath().toString();
            String fieldName = fieldPath.substring(fieldPath.lastIndexOf(".") + 1);
            if (invalidFields.containsKey(fieldName)) {
                invalidFields.get(fieldName).add(constraintViolation.getMessage());
            } else {
                List<String> reasons = new ArrayList<>();
                reasons.add(constraintViolation.getMessage());
                invalidFields.put(fieldName, reasons);
            }
        });
        return ValidationExceptionDto.builder()
                .title("VALIDATION_ERROR")
                .detail("Ошибка валидации")
                .status(HttpStatus.BAD_REQUEST.value())
                .invalidFields(invalidFields)
                .build();
    }

    @ExceptionHandler(BaseApiException.class)
    public ResponseEntity<BaseExceptionDto> onRequestException(BaseApiException e) {
        return new ResponseEntity<>(BaseExceptionDto.builder()
                .title(e.getTitle())
                .detail(e.getDetail())
                .status(e.getHttpStatus().value())
                .build(),e.getHttpStatus());
    }

}
