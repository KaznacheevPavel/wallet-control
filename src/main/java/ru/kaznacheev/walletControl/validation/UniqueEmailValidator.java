package ru.kaznacheev.walletControl.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.validation.constraint.UniqueEmail;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository.existsByEmail(email)) {
            return false;
        }
        return true;
    }

}
