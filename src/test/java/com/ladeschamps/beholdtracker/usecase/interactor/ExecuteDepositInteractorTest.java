package com.ladeschamps.beholdtracker.usecase.interactor;

import com.ladeschamps.beholdtracker.domain.Account;
import com.ladeschamps.beholdtracker.domain.exception.AccountNotFoundException;
import com.ladeschamps.beholdtracker.usecase.port.AccountRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UseCase - ExecuteDepositInteractor Unit Tests")
class ExecuteDepositInteractorTest {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    private ExecuteDepositInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new ExecuteDepositInteractor(accountRepositoryPort);
    }

    @Test
    @DisplayName("Should successfully deposit cash and persist updated account state")
    void shouldExecuteDepositSuccessfully() {
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal currentBalance = new BigDecimal("1000.00");
        BigDecimal depositAmount = new BigDecimal("500.00");
        Account existingAccount = new Account(accountId, "Rico", initialBalance, currentBalance, LocalDateTime.now());

        when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepositoryPort.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = interactor.execute(accountId, depositAmount);

        assertNotNull(result);
        assertEquals(new BigDecimal("1500.00"), result.getCurrentBalance());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryPort, times(1)).save(captor.capture());
        assertEquals(new BigDecimal("1500.00"), captor.getValue().getCurrentBalance());
    }

    @Test
    @DisplayName("Should throw AccountNotFoundException when depositing to non-existent account")
    void shouldThrowExceptionWhenAccountNotFound() {
        Long nonExistentAccountId = 404L;
        BigDecimal depositAmount = new BigDecimal("100.00");

        when(accountRepositoryPort.findById(nonExistentAccountId)).thenReturn(Optional.empty());

        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> interactor.execute(nonExistentAccountId, depositAmount)
        );

        assertEquals(nonExistentAccountId, exception.getAccountId());
        verify(accountRepositoryPort, never()).save(any(Account.class));
    }
}
