package ru.kaznacheev.walletControl.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kaznacheev.walletControl.validation.constraint.UsernamePattern;

import java.util.regex.Pattern;

public class UsernamePatternValidator implements ConstraintValidator<UsernamePattern, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        Pattern pattern = Pattern.compile("^[a-zA-Z].*$");
        if (!pattern.matcher(username).matches()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Имя пользователя должно начинаться с буквы")
                    .addConstraintViolation();
            valid = false;
        }
        pattern = Pattern.compile("^[a-zA-Z0-9_-]+$");
        if (!pattern.matcher(username).matches()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Допустимые символы для имени пользователя: буквы, цифры, - и _")
                    .addConstraintViolation();
            valid = false;
        }
        pattern = Pattern.compile("^.*[-_]{2,}.*$");
        if (pattern.matcher(username).matches()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("В имени пользователя запрещены два - или _ подряд")
                    .addConstraintViolation();
            valid = false;
        }
        pattern = Pattern.compile("^.*[a-zA-Z0-9]$");
        if (!pattern.matcher(username).matches()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Имя пользователя должно заканчиваться на букву или цифру")
                    .addConstraintViolation();
            valid = false;
        }
        return valid;
    }

}
