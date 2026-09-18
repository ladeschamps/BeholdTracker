package com.ladeschamps.beholdertracker.domain;

import com.ladeschamps.beholdertracker.domain.exception.InsufficientBalanceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Pure Domain Entity representing a brokerage or cash holding account.
 * Encapsulates core business invariants and balance calculation logic without framework dependencies.
 */
public class Account {

    private final Long id;
    private final String name;
    private final BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private final LocalDateTime createdAt;

    public Account(String name, BigDecimal initialBalance) {
        this(null, name, initialBalance, null, null);
    }

    public Account(Long id, String name, BigDecimal initialBalance, BigDecimal currentBalance, LocalDateTime createdAt) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Account name must not be null or blank");
        }
        BigDecimal initBalance = initialBalance != null ? initialBalance : BigDecimal.ZERO;
        if (initBalance.signum() < 0) {
            throw new IllegalArgumentException("Initial balance must not be negative");
        }
        this.id = id;
        this.name = name.trim();
        this.initialBalance = initBalance;
        this.currentBalance = currentBalance != null ? currentBalance : initBalance;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    /**
     * Credits liquid balance into the account.
     *
     * @param amount the non-null, positive amount to deposit
     */
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Deposit amount must be strictly positive");
        }
        this.currentBalance = this.currentBalance.add(amount);
    }

    /**
     * Debits liquid balance from the account.
     *
     * @param amount the non-null, positive amount to withdraw
     * @throws InsufficientBalanceException if withdrawal exceeds current balance
     */
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be strictly positive");
        }
        if (this.currentBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(this.id, this.currentBalance, amount);
        }
        this.currentBalance = this.currentBalance.subtract(amount);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(name, account.name) &&
                areEqual(initialBalance, account.initialBalance) &&
                areEqual(currentBalance, account.currentBalance) &&
                Objects.equals(createdAt, account.createdAt);
    }

    private static boolean areEqual(BigDecimal a, BigDecimal b) {
        if (a == null) {
            return b == null;
        }
        return b != null && a.compareTo(b) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                initialBalance != null ? initialBalance.stripTrailingZeros() : null,
                currentBalance != null ? currentBalance.stripTrailingZeros() : null,
                createdAt
        );
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", initialBalance=" + initialBalance +
                ", currentBalance=" + currentBalance +
                ", createdAt=" + createdAt +
                '}';
    }
}
