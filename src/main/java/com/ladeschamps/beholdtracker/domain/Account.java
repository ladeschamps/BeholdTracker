package com.ladeschamps.beholdtracker.domain;

import com.ladeschamps.beholdtracker.domain.exception.InsufficientBalanceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Pure Domain Entity representing a liquid investment or brokerage account.
 * Enforces business invariants such as non-negative balances.
 */
public class Account {

    private final Long id;
    private final String name;
    private final BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private final LocalDateTime createdAt;

    /**
     * Factory constructor for creating a new Account instance.
     */
    public Account(String name, BigDecimal initialBalance) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name must not be blank.");
        }
        BigDecimal normalizedInitial = initialBalance != null ? initialBalance : BigDecimal.ZERO;
        if (normalizedInitial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }

        this.id = null;
        this.name = name.trim();
        this.initialBalance = normalizedInitial;
        this.currentBalance = normalizedInitial;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Reconstitution constructor for reconstructing existing entities from persistence.
     */
    public Account(Long id, String name, BigDecimal initialBalance, BigDecimal currentBalance, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.initialBalance = initialBalance;
        this.currentBalance = currentBalance;
        this.createdAt = createdAt;
    }

    /**
     * Executes a deposit into the account, increasing current liquid balance.
     */
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be strictly positive.");
        }
        this.currentBalance = this.currentBalance.add(amount);
    }

    /**
     * Executes a cash withdrawal from the account, ensuring non-negative balance invariant.
     */
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be strictly positive.");
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

    private static boolean areEqual(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.compareTo(b) == 0;
    }
}
