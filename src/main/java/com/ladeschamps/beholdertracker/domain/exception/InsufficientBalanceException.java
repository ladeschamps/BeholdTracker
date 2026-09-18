package com.ladeschamps.beholdertracker.domain.exception;

import java.math.BigDecimal;

/**
 * Domain exception thrown when a withdrawal operation would result in a negative account balance.
 */
public class InsufficientBalanceException extends RuntimeException {

    private final Long accountId;
    private final BigDecimal currentBalance;
    private final BigDecimal withdrawalAmount;

    public InsufficientBalanceException(Long accountId, BigDecimal currentBalance, BigDecimal withdrawalAmount) {
        super("Insufficient balance for account ID " + accountId + ": current balance is " + currentBalance + ", attempted withdrawal is " + withdrawalAmount);
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
