package ru.kaznacheev.walletControl.service;

import jakarta.validation.Valid;
import ru.kaznacheev.walletControl.dto.NewUserDto;

public interface UserService {
    void createUser(@Valid NewUserDto newUserDto);
}
