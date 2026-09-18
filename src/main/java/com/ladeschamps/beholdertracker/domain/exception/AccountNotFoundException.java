package com.ladeschamps.beholdertracker.domain.exception;

/**
 * Domain exception thrown when an account cannot be located by its identifier.
 */
public class AccountNotFoundException extends RuntimeException {

    private final Long accountId;

    public AccountNotFoundException(Long accountId) {
        super("Account with ID " + accountId + " not found");
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
