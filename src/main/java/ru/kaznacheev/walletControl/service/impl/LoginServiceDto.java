package ru.kaznacheev.walletControl.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.walletControl.dto.LoginDto;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.exception.ExceptionFactory;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.service.LoginService;
import ru.kaznacheev.walletControl.util.JwtUtil;

import java.util.Optional;

@Service
@Validated
@AllArgsConstructor
public class LoginServiceDto implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public String login(LoginDto loginDto) {
        Optional<User> user = userRepository.findByUsername(loginDto.getUsername());
        if (user.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            throw ExceptionFactory.invalidCredentialsException();
        }
        if (!user.get().isActivated()) {
            throw ExceptionFactory.notActivatedAccount();
        }
        return JwtUtil.generateJwt(user.get());
    }

}
