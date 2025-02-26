package ru.kaznacheev.walletControl.service.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.walletControl.dto.NewUserDto;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.exception.ExceptionFactory;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.service.UserService;
import ru.kaznacheev.walletControl.service.VerificationTokenService;

@Service
@Validated
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final VerificationTokenService verificationTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(@Valid NewUserDto newUserDto) {
        if (userRepository.existsByUsername(newUserDto.getUsername())) {
            throw ExceptionFactory.invalidUsernameException();
        }
        if (userRepository.existsByEmail(newUserDto.getEmail())) {
            throw ExceptionFactory.invalidEmailException();
        }

        User user = User.builder()
                .username(newUserDto.getUsername())
                .email(newUserDto.getEmail())
                .password(encode(newUserDto.getPassword()))
                .build();
        userRepository.save(user);
        verificationTokenService.createVerificationToken(user);
    }

    private String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
