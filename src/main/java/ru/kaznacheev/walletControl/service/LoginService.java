package ru.kaznacheev.walletControl.service;

import jakarta.validation.Valid;
import ru.kaznacheev.walletControl.dto.LoginRequest;

public interface LoginService {
    String login(@Valid LoginRequest loginRequest);
}
