package com.ladeschamps.beholdertracker.domain.exception;

/**
 * Domain exception thrown when an account cannot be located by its identifier.
 */
public class AccountNotFoundException extends RuntimeException {

    private final Long accountId;

    public AccountNotFoundException(Long accountId) {
        super(String.format("Account with ID %d not found", accountId));
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
