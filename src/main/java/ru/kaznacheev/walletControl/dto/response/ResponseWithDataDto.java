package ru.kaznacheev.walletControl.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@SuperBuilder
@Getter
public class ResponseWithDataDto extends BaseResponseDto {
    private final Map<String, String> data;
}
