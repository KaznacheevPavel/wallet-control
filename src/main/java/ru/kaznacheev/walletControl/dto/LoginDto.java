package ru.kaznacheev.walletControl.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginDto {

    @NotBlank(message = "Имя пользователя не может быть пустым")
    private final String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private final String password;

}
