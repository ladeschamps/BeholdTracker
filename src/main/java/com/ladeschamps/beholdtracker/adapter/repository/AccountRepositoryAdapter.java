package com.ladeschamps.beholdtracker.adapter.repository;

import com.ladeschamps.beholdtracker.domain.Account;
import com.ladeschamps.beholdtracker.usecase.port.AccountRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Persistence adapter implementing the AccountRepositoryPort interface,
 * delegating queries to Spring Data JPA and mapping entities through AccountMapper.
 */
@Component
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountJpaRepository accountJpaRepository;

    public AccountRepositoryAdapter(AccountJpaRepository accountJpaRepository) {
        this.accountJpaRepository = Objects.requireNonNull(accountJpaRepository, "accountJpaRepository must not be null");
    }

    @Override
    public Account save(Account account) {
        AccountDatabaseEntity entity = AccountMapper.toEntity(account);
        AccountDatabaseEntity savedEntity = accountJpaRepository.save(entity);
        return AccountMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Account> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return accountJpaRepository.findById(id).map(AccountMapper::toDomain);
    }

    @Override
    public List<Account> findAll() {
        return accountJpaRepository.findAll().stream()
                .map(AccountMapper::toDomain)
                .toList();
    }
}
