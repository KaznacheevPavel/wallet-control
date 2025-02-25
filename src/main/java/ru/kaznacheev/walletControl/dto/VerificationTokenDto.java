package ru.kaznacheev.walletControl.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.UUID;

@AllArgsConstructor
@Getter
public class VerificationTokenDto {
    @NotBlank(message = "Токен не может быть пустым")
    @UUID(message = "Неверный формат токена")
    private final String token;
}
