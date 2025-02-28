package ru.kaznacheev.walletControl.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kaznacheev.walletControl.entity.OperationType;
import ru.kaznacheev.walletControl.validation.constraint.ExistingOperationType;

import java.util.Arrays;

public class ExistingOperationTypeValidator implements ConstraintValidator<ExistingOperationType, String> {

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(OperationType.values()).anyMatch((value) -> value.name().equals(type));
    }

}
