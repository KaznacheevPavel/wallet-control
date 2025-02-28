package ru.kaznacheev.walletControl.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.kaznacheev.walletControl.validation.ExistingTimeZoneValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingTimeZoneValidator.class)
public @interface ExistingTimeZone {
    String message() default "incorrect time zone";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
