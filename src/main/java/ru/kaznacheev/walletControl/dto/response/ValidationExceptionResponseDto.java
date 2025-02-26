package ru.kaznacheev.walletControl.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;


@Getter
@SuperBuilder
public class ValidationExceptionResponseDto extends BaseResponseDto {
    @JsonProperty(value = "invalid_fields")
    private final Map<String, List<String>> invalidFields;
}
