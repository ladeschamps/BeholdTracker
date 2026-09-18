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
        super(String.format("Insufficient balance for account ID %s: current balance is %s, attempted withdrawal is %s",
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
