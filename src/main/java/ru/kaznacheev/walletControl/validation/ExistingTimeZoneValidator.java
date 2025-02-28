package ru.kaznacheev.walletControl.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kaznacheev.walletControl.validation.constraint.ExistingTimeZone;

import java.time.ZoneId;

public class ExistingTimeZoneValidator implements ConstraintValidator<ExistingTimeZone, String> {

    @Override
    public boolean isValid(String timeZone, ConstraintValidatorContext constraintValidatorContext) {
        return ZoneId.getAvailableZoneIds().contains(timeZone);
    }

}
