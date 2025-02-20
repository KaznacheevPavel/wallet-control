package ru.kaznacheev.walletControl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationViolationsResponse {
    private List<ValidationViolation> validationViolations;
}
