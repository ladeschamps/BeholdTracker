package com.ladeschamps.beholdertracker.adapter.repository;

import com.ladeschamps.beholdertracker.domain.Account;

/**
 * Mapper utility for bidirectional conversions between Domain Account and Persistence AccountDatabaseEntity.
 */
public final class AccountMapper {

    private AccountMapper() {
    }

    /**
     * Converts a JPA persistence entity to a pure domain Account entity.
     *
     * @param entity the JPA entity
     * @return the domain Account entity, or null if entity is null
     */
    public static Account toDomain(AccountDatabaseEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Account(
                entity.getId(),
                entity.getName(),
                entity.getInitialBalance(),
                entity.getCurrentBalance(),
                entity.getCreatedAt()
        );
    }

    /**
     * Converts a pure domain Account entity to a JPA persistence entity.
     *
     * @param domain the domain Account entity
     * @return the JPA persistence entity, or null if domain is null
     */
    public static AccountDatabaseEntity toEntity(Account domain) {
        if (domain == null) {
            return null;
        }
        return new AccountDatabaseEntity(
                domain.getId(),
                domain.getName(),
                domain.getInitialBalance(),
                domain.getCurrentBalance(),
                domain.getCreatedAt()
        );
    }
}
