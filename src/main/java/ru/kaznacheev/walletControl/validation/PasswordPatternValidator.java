package ru.kaznacheev.walletControl.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kaznacheev.walletControl.validation.constraint.PasswordPattern;

import java.util.regex.Pattern;

public class PasswordPatternValidator implements ConstraintValidator<PasswordPattern, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        Pattern pattern = Pattern.compile("^.*[a-z]+.*$");
        if (!pattern.matcher(password).matches()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Пароль должен иметь хотя бы одну английскую букву")
                    .addConstraintViolation();
            valid = false;
        }
        pattern = Pattern.compile("^.*[A-Z]+.*$");
        if (!pattern.matcher(password).matches()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Пароль должен иметь хотя бы одну заглавную английскую букву")
                    .addConstraintViolation();
            valid = false;
        }
        pattern = Pattern.compile("^.*[0-9]+.*$");
        if (!pattern.matcher(password).matches()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Пароль должен иметь хотя бы одну цифру")
                    .addConstraintViolation();
            valid = false;
        }
        pattern = Pattern.compile("^.*[#?!@$%^&*_-]+.*$");
        if (!pattern.matcher(password).matches()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Пароль должен иметь хотя бы один специальный символ из #?!@$%^&*_-")
                    .addConstraintViolation();
            valid = false;
        }
        return valid;
    }

}
