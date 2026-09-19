package com.ladeschamps.beholdtracker.usecase.interactor;

import com.ladeschamps.beholdtracker.domain.Account;
import com.ladeschamps.beholdtracker.usecase.port.AccountRepositoryPort;
import com.ladeschamps.beholdtracker.usecase.port.RegisterAccountUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Interactor implementing the business logic for registering a new Account.
 */
@Service
@Transactional
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
