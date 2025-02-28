package ru.kaznacheev.walletControl.service;

import jakarta.validation.Valid;
import ru.kaznacheev.walletControl.dto.NewUserRequest;
import ru.kaznacheev.walletControl.entity.User;

public interface UserService {
    void createUser(@Valid NewUserRequest newUserRequest);
    User getUser(String username);
}
