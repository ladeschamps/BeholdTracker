package com.ladeschamps.beholdertracker.usecase.interactor;

import com.ladeschamps.beholdertracker.domain.Account;
import com.ladeschamps.beholdertracker.usecase.port.AccountRepositoryPort;
import com.ladeschamps.beholdertracker.usecase.port.RegisterAccountUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Interactor implementation for registering a new Account.
 */
@Service
public class RegisterAccountInteractor implements RegisterAccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public RegisterAccountInteractor(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = Objects.requireNonNull(accountRepositoryPort, "accountRepositoryPort must not be null");
    }

    @Override
    public Account register(String name, BigDecimal initialBalance) {
        Account newAccount = new Account(name, initialBalance);
        return accountRepositoryPort.save(newAccount);
    }
}
