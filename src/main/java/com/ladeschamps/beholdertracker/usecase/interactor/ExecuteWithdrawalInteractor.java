package com.ladeschamps.beholdertracker.usecase.interactor;

import com.ladeschamps.beholdertracker.domain.Account;
import com.ladeschamps.beholdertracker.domain.exception.AccountNotFoundException;
import com.ladeschamps.beholdertracker.usecase.port.AccountRepositoryPort;
import com.ladeschamps.beholdertracker.usecase.port.ExecuteWithdrawalUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Interactor implementation for withdrawing funds from an existing Account.
 */
@Service
public class ExecuteWithdrawalInteractor implements ExecuteWithdrawalUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public ExecuteWithdrawalInteractor(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = Objects.requireNonNull(accountRepositoryPort, "accountRepositoryPort must not be null");
    }

    @Override
    public Account execute(Long accountId, BigDecimal amount) {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID must not be null");
        }
        Account account = accountRepositoryPort.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        // Domain method withdraw will throw InsufficientBalanceException if amount > currentBalance,
        // which prevents persistence save from executing.
        account.withdraw(amount);
        return accountRepositoryPort.save(account);
    }
}
