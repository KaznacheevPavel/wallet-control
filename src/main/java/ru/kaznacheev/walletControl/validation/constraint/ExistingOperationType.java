package ru.kaznacheev.walletControl.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.kaznacheev.walletControl.validation.ExistingOperationTypeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingOperationTypeValidator.class)
public @interface ExistingOperationType {
    String message() default "incorrect operation type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
