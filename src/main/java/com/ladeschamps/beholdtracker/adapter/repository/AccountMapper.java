package com.ladeschamps.beholdtracker.adapter.repository;

import com.ladeschamps.beholdtracker.domain.Account;

/**
 * Static mapper translating between pure domain Account entities and persistence entities.
 */
public final class AccountMapper {

    private AccountMapper() {
    }

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
