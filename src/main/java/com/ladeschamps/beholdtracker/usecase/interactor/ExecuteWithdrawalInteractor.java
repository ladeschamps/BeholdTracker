package com.ladeschamps.beholdtracker.usecase.interactor;

import com.ladeschamps.beholdtracker.domain.Account;
import com.ladeschamps.beholdtracker.domain.exception.AccountNotFoundException;
import com.ladeschamps.beholdtracker.usecase.port.AccountRepositoryPort;
import com.ladeschamps.beholdtracker.usecase.port.ExecuteWithdrawalUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Interactor implementing the business logic for executing cash withdrawals.
 */
@Service
@Transactional
public class ExecuteWithdrawalInteractor implements ExecuteWithdrawalUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public ExecuteWithdrawalInteractor(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = Objects.requireNonNull(accountRepositoryPort, "accountRepositoryPort must not be null");
    }

    @Override
    public Account execute(Long accountId, BigDecimal amount) {
        Account account = accountRepositoryPort.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        account.withdraw(amount);
        return accountRepositoryPort.save(account);
    }
}
