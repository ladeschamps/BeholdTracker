package com.ladeschamps.beholdtracker.usecase.port;

import com.ladeschamps.beholdtracker.domain.Account;

import java.util.List;
import java.util.Optional;

/**
 * Outbound port defining persistent storage operations for Accounts.
 */
public interface AccountRepositoryPort {

    Account save(Account account);

    Optional<Account> findById(Long id);

    List<Account> findAll();
}
