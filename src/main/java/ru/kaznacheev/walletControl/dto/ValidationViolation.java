package ru.kaznacheev.walletControl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationViolation {
    private String field;
    private String message;
    private List<String> details;
}
