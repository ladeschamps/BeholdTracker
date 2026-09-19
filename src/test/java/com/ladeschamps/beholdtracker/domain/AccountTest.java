package com.ladeschamps.beholdtracker.domain;

import com.ladeschamps.beholdtracker.domain.exception.InsufficientBalanceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Domain - Account Entity Unit Tests")
class AccountTest {

    @Test
    @DisplayName("Should create account with correct initial and current balances")
    void shouldCreateAccountSuccessfully() {
        Account account = new Account("Clear Corretora", new BigDecimal("1500.00"));

        assertEquals("Clear Corretora", account.getName());
        assertEquals(new BigDecimal("1500.00"), account.getInitialBalance());
        assertEquals(new BigDecimal("1500.00"), account.getCurrentBalance());
        assertNotNull(account.getCreatedAt());
    }

    @Test
    @DisplayName("Should default initial balance to zero when null is provided")
    void shouldDefaultInitialBalanceToZeroWhenNull() {
        Account account = new Account("NuInvest", null);

        assertEquals(BigDecimal.ZERO, account.getInitialBalance());
        assertEquals(BigDecimal.ZERO, account.getCurrentBalance());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating account with blank name")
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Account("   ", new BigDecimal("100.00")));
        assertThrows(IllegalArgumentException.class, () -> new Account(null, new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating account with negative initial balance")
    void shouldThrowExceptionWhenInitialBalanceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Account("XP", new BigDecimal("-50.00")));
    }

    @Test
    @DisplayName("Should deposit amount and increase current balance")
    void shouldDepositAmountSuccessfully() {
        Account account = new Account("BTG Pactual", new BigDecimal("1000.00"));

        account.deposit(new BigDecimal("500.50"));

        assertEquals(new BigDecimal("1500.50"), account.getCurrentBalance());
        assertEquals(new BigDecimal("1000.00"), account.getInitialBalance());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when deposit amount is zero or negative")
    void shouldThrowExceptionOnInvalidDepositAmount() {
        Account account = new Account("BTG Pactual", new BigDecimal("1000.00"));

        assertThrows(IllegalArgumentException.class, () -> account.deposit(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(new BigDecimal("-100.00")));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(null));
    }

    @Test
    @DisplayName("Should withdraw amount and decrease current balance")
    void shouldWithdrawAmountSuccessfully() {
        Account account = new Account("Rico", new BigDecimal("1000.00"));

        account.withdraw(new BigDecimal("400.00"));

        assertEquals(new BigDecimal("600.00"), account.getCurrentBalance());
    }

    @Test
    @DisplayName("Should allow withdrawal that reduces current balance exactly to zero")
    void shouldAllowWithdrawalToZeroBalance() {
        Account account = new Account("Rico", new BigDecimal("1000.00"));

        account.withdraw(new BigDecimal("1000.00"));

        assertEquals(new BigDecimal("0.00"), account.getCurrentBalance());
    }

    @Test
    @DisplayName("Should throw InsufficientBalanceException and prevent balance change when withdrawal exceeds current balance")
    void shouldThrowInsufficientBalanceExceptionWhenWithdrawalExceedsBalance() {
        Account account = new Account(1L, "Genial", new BigDecimal("500.00"), new BigDecimal("500.00"), LocalDateTime.now());

        InsufficientBalanceException exception = assertThrows(
                InsufficientBalanceException.class,
                () -> account.withdraw(new BigDecimal("500.01"))
        );

        assertEquals(1L, exception.getAccountId());
        assertEquals(new BigDecimal("500.00"), exception.getCurrentBalance());
        assertEquals(new BigDecimal("500.01"), exception.getWithdrawalAmount());
        // Invariant: Balance must remain unaltered
        assertEquals(new BigDecimal("500.00"), account.getCurrentBalance());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when withdrawal amount is zero or negative")
    void shouldThrowExceptionOnInvalidWithdrawalAmount() {
        Account account = new Account("Genial", new BigDecimal("500.00"));

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(new BigDecimal("-50.00")));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(null));
    }
}
