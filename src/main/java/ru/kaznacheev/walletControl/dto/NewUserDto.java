package ru.kaznacheev.walletControl.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.walletControl.validation.constraint.PasswordPattern;
import ru.kaznacheev.walletControl.validation.constraint.UsernamePattern;


@AllArgsConstructor
@Getter
public class NewUserDto {

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 6, message = "Имя пользователя должно быть не меньше 6 символов")
    @Size(max = 32, message = "Имя пользователя должно быть не больше 32 символов")
    @UsernamePattern //^[a-zA-Z]([_-]?[a-zA-Z0-9]){5,31}$
    private final String username;

    @NotBlank(message = "Email не может быть пустым")
    @Size(max = 254, message = "Email должен быть не больше 254 символов")
    @Email(message = "Неверный формат email")
    private final String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен быть не меньше 8 символов")
    @PasswordPattern //^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*_-]).{8,}$
    private final String password;
}
