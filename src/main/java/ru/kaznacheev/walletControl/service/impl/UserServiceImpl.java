package ru.kaznacheev.walletControl.service.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.kaznacheev.walletControl.dto.NewUserDto;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.repository.UserRepository;
import ru.kaznacheev.walletControl.service.UserService;

@Service
@Validated
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(@Valid NewUserDto newUserDto) {
        User user = User.builder()
                .username(newUserDto.getUsername())
                .email(newUserDto.getEmail())
                .password(encode(newUserDto.getPassword()))
                .build();
        userRepository.save(user);
    }

    private String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
