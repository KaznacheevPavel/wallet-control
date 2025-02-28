package ru.kaznacheev.walletControl.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.walletControl.dto.LoginRequest;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.exception.ExceptionFactory;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.service.LoginService;
import ru.kaznacheev.walletControl.util.JwtUtil;

import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public String login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            throw ExceptionFactory.invalidCredentialsException();
        }
        if (!user.get().isActivated()) {
            throw ExceptionFactory.notActivatedAccount();
        }
        return JwtUtil.generateJwt(user.get());
    }

}
