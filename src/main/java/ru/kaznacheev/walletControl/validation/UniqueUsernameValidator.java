package ru.kaznacheev.walletControl.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.validation.constraint.UniqueUsername;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        return true;
    }

}
