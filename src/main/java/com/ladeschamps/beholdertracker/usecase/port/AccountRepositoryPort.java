package com.ladeschamps.beholdertracker.usecase.port;

import com.ladeschamps.beholdertracker.domain.Account;

import java.util.List;
import java.util.Optional;

/**
 * Outbound port defining persistence operations for domain Account entities.
 */
public interface AccountRepositoryPort {

    Account save(Account account);

    Optional<Account> findById(Long id);

    List<Account> findAll();
}
