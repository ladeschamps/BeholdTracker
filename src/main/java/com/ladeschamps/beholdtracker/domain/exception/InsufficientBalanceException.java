package com.ladeschamps.beholdtracker.domain.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    private final Long accountId;
    private final BigDecimal currentBalance;
    private final BigDecimal withdrawalAmount;

    public InsufficientBalanceException(Long accountId, BigDecimal currentBalance, BigDecimal withdrawalAmount) {
        super(String.format("Account ID %d has insufficient balance (Current: %s, Attempted withdrawal: %s)",
                accountId, currentBalance, withdrawalAmount));
        this.accountId = accountId;
        this.currentBalance = currentBalance;
        this.withdrawalAmount = withdrawalAmount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }
}
