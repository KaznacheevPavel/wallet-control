package ru.kaznacheev.walletControl.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kaznacheev.walletControl.dto.NewOperationRequest;
import ru.kaznacheev.walletControl.dto.TimeZoneRequest;
import ru.kaznacheev.walletControl.dto.response.OperationForResponse;
import ru.kaznacheev.walletControl.entity.Category;
import ru.kaznacheev.walletControl.entity.Operation;
import ru.kaznacheev.walletControl.entity.OperationType;
import ru.kaznacheev.walletControl.entity.User;
import ru.kaznacheev.walletControl.repository.OperationRepository;
import ru.kaznacheev.walletControl.service.CategoryService;
import ru.kaznacheev.walletControl.service.UserService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {

    @Mock
    private OperationRepository operationRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserService userService;

    @InjectMocks
    private OperationServiceImpl operationService;

    @Test
    @DisplayName("Should save user operation")
    void givenValidOperationRequest_whenCreateOperation_thenSaveOperation() {
        Category category = Category.builder().title("Авто").build();
        when(categoryService.getCategory("Авто")).thenReturn(category);

        User user = User.builder().username("firstUser").build();
        when(userService.getUser("firstUser")).thenReturn(user);

        NewOperationRequest newOperationRequest = new NewOperationRequest("Авто", "EXPENSE", new BigDecimal(50), "Покупка шин");

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn("firstUser");

            try (MockedStatic<Instant> mockedInstant = mockStatic(Instant.class)) {
                Instant instant = Instant.parse("2025-01-01T12:00:00Z");
                mockedInstant.when(Instant::now).thenReturn(instant);

                Operation expectedOperation = Operation.builder()
                        .user(user)
                        .category(category)
                        .createdAt(instant)
                        .type(OperationType.EXPENSE)
                        .amount(new BigDecimal(50))
                        .description("Покупка шин")
                        .build();
                ArgumentCaptor<Operation> operationArgumentCaptor = ArgumentCaptor.forClass(Operation.class);

                operationService.createOperation(newOperationRequest);

                verify(operationRepository).save(operationArgumentCaptor.capture());
                Operation savedOperation = operationArgumentCaptor.getValue();
                assertThat(savedOperation).usingRecursiveComparison().isEqualTo(expectedOperation);
            }
        }
    }

    @Test
    @DisplayName("Should return all user operations")
    void givenValidOperationList_whenGetAllUserOperations_thenReturnUserOperations() {
        User user = User.builder().username("firstUser").build();
        when(userService.getUser("firstUser")).thenReturn(user);

        Category productsCategory = Category.builder().title("Продукты").build();
        Category carCategory = Category.builder().title("Авто").build();

        List<Operation> operations = List.of(
                Operation.builder()
                        .user(user)
                        .category(productsCategory)
                        .createdAt(Instant.ofEpochSecond(150000))
                        .type(OperationType.EXPENSE)
                        .amount(new BigDecimal(123))
                        .build(),
                Operation.builder()
                        .user(user)
                        .category(carCategory)
                        .createdAt(Instant.ofEpochSecond(180000))
                        .type(OperationType.EXPENSE)
                        .amount(new BigDecimal(908))
                        .build()
        );
        when(operationRepository.findAllByUser(user)).thenReturn(operations);

        TimeZoneRequest timeZoneRequest = new TimeZoneRequest("UTC");

        List<OperationForResponse> expectedOperations = List.of(
                OperationForResponse.builder()
                        .createdAt(LocalDateTime.ofInstant(operations.get(0).getCreatedAt(), ZoneId.of(timeZoneRequest.getTimeZone())))
                        .category("Продукты")
                        .type("EXPENSE")
                        .amount(new BigDecimal(123))
                        .build(),
                OperationForResponse.builder()
                        .createdAt(LocalDateTime.ofInstant(operations.get(1).getCreatedAt(), ZoneId.of(timeZoneRequest.getTimeZone())))
                        .category("Авто")
                        .type("EXPENSE")
                        .amount(new BigDecimal(908))
                        .build()
        );

        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn("firstUser");

            List<OperationForResponse> actualOperations = operationService.getAllUserOperation(timeZoneRequest);

            assertThat(actualOperations)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(expectedOperations);
        }
    }

}