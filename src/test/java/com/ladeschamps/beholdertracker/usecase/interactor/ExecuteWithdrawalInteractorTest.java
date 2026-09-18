package com.ladeschamps.beholdertracker.usecase.interactor;

import com.ladeschamps.beholdertracker.domain.Account;
import com.ladeschamps.beholdertracker.domain.exception.AccountNotFoundException;
import com.ladeschamps.beholdertracker.domain.exception.InsufficientBalanceException;
import com.ladeschamps.beholdertracker.usecase.port.AccountRepositoryPort;
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
@DisplayName("UseCase - ExecuteWithdrawalInteractor Unit Tests")
class ExecuteWithdrawalInteractorTest {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    private ExecuteWithdrawalInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new ExecuteWithdrawalInteractor(accountRepositoryPort);
    }

    @Test
    @DisplayName("Should successfully withdraw cash and persist updated account state")
    void shouldExecuteWithdrawalSuccessfully() {
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("2000.00");
        BigDecimal currentBalance = new BigDecimal("2000.00");
        BigDecimal withdrawalAmount = new BigDecimal("750.00");
        Account existingAccount = new Account(accountId, "Clear Corretora", initialBalance, currentBalance, LocalDateTime.now());

        when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepositoryPort.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = interactor.execute(accountId, withdrawalAmount);

        assertNotNull(result);
        assertEquals(new BigDecimal("1250.00"), result.getCurrentBalance());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepositoryPort, times(1)).save(captor.capture());
        assertEquals(new BigDecimal("1250.00"), captor.getValue().getCurrentBalance());
    }

    @Test
    @DisplayName("Should throw InsufficientBalanceException and block database persistence when balance is insufficient")
    void shouldThrowExceptionAndBlockPersistenceWhenBalanceInsufficient() {
        Long accountId = 1L;
        BigDecimal initialBalance = new BigDecimal("500.00");
        BigDecimal currentBalance = new BigDecimal("500.00");
        BigDecimal excessiveWithdrawal = new BigDecimal("500.01");
        Account existingAccount = new Account(accountId, "XP Investimentos", initialBalance, currentBalance, LocalDateTime.now());

        when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.of(existingAccount));

        InsufficientBalanceException exception = assertThrows(
                InsufficientBalanceException.class,
                () -> interactor.execute(accountId, excessiveWithdrawal)
        );

        assertEquals(accountId, exception.getAccountId());
        assertEquals(new BigDecimal("500.00"), exception.getCurrentBalance());
        assertEquals(excessiveWithdrawal, exception.getWithdrawalAmount());

        // CRITICAL INVARIANT: Ensure repository save is NEVER invoked
        verify(accountRepositoryPort, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw AccountNotFoundException and block persistence when account does not exist")
    void shouldThrowExceptionWhenAccountNotFound() {
        Long nonExistentAccountId = 999L;
        BigDecimal withdrawalAmount = new BigDecimal("100.00");

        when(accountRepositoryPort.findById(nonExistentAccountId)).thenReturn(Optional.empty());

        AccountNotFoundException exception = assertThrows(
                AccountNotFoundException.class,
                () -> interactor.execute(nonExistentAccountId, withdrawalAmount)
        );

        assertEquals(nonExistentAccountId, exception.getAccountId());
        verify(accountRepositoryPort, never()).save(any(Account.class));
    }
}
