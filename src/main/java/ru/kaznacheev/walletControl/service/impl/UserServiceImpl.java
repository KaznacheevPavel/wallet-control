package ru.kaznacheev.walletControl.service.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.walletControl.dto.NewUserRequest;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.exception.ExceptionFactory;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.service.UserService;
import ru.kaznacheev.walletControl.service.VerificationTokenService;

import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final VerificationTokenService verificationTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void createUser(@Valid NewUserRequest newUserRequest) {
        if (userRepository.existsByUsername(newUserRequest.getUsername())) {
            throw ExceptionFactory.invalidUsernameException();
        }
        if (userRepository.existsByEmail(newUserRequest.getEmail())) {
            throw ExceptionFactory.invalidEmailException();
        }

        User user = User.builder()
                .username(newUserRequest.getUsername())
                .email(newUserRequest.getEmail())
                .password(encode(newUserRequest.getPassword()))
                .build();
        userRepository.save(user);
        verificationTokenService.createVerificationToken(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw ExceptionFactory.userNotFound();
        }
        return user.get();
    }

    private String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
