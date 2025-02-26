package ru.kaznacheev.walletControl.service;

import jakarta.validation.Valid;
import ru.kaznacheev.walletControl.dto.LoginDto;

public interface LoginService {
    String login(@Valid LoginDto loginDto);
}
