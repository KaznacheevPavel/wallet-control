package ru.kaznacheev.walletControl.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.walletControl.validation.constraint.ExistingTimeZone;

@AllArgsConstructor
@Getter
public class TimeZoneRequest {
    @NotBlank(message = "Часовой пояс не может быть пустым")
    @ExistingTimeZone(message = "Неверный часовой пояс")
    private final String timeZone;
}
