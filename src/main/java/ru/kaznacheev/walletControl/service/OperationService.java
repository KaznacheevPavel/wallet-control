package ru.kaznacheev.walletControl.service;

import jakarta.validation.Valid;
import ru.kaznacheev.walletControl.dto.NewOperationRequest;
import ru.kaznacheev.walletControl.dto.TimeZoneRequest;
import ru.kaznacheev.walletControl.dto.response.OperationForResponse;

import java.util.List;

public interface OperationService {
    void createOperation(@Valid NewOperationRequest newOperationRequest);
    List<OperationForResponse> getAllUserOperation(@Valid TimeZoneRequest timeZoneRequest);

}
