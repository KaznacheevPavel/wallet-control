package ru.kaznacheev.walletControl.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class BaseApiException extends RuntimeException{
    private final String title;
    private final HttpStatus httpStatus;
    private final String detail;
}
