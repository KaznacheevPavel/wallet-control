package ru.kaznacheev.walletControl.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
public class ResponseWithData extends BaseResponse {
    private final Map<String, ?> data;
}
