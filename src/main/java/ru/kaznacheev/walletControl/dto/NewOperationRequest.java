package ru.kaznacheev.walletControl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kaznacheev.walletControl.validation.constraint.ExistingOperationType;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class NewOperationRequest {

    @NotBlank(message = "Категория операции не может быть пустая")
    private final String category;

    @NotBlank(message = "Тип операции не может быть пустым")
    @ExistingOperationType(message = "Неверный тип операции")
    private final String type;

    @NotNull(message = "Сумма операции не может быть пустая")
    @DecimalMin(value = "0", inclusive = false, message = "Сумма операции не может быть меньше или равно нулю")
    @Digits(integer = 10, fraction = 2, message = "Неверный формат суммы операции")
    private final BigDecimal amount;

    @JsonProperty(required = true)
    @Size(max = 255, message = "Максимальное количество символов для описания - 255")
    private final String description;

}
