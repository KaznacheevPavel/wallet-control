package ru.kaznacheev.walletControl.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;


@Getter
@SuperBuilder
public class ValidationExceptionDto extends BaseExceptionDto {
    private final Map<String, List<String>> invalidFields;
}
