package com.ladeschamps.beholdertracker.adapter.controller.dto;

import com.ladeschamps.beholdertracker.domain.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response payload representing account state returned by REST endpoints.
 */
public record AccountResponse(
        Long id,
        String name,
        BigDecimal initialBalance,
        BigDecimal currentBalance,
        LocalDateTime createdAt
) {

    public static AccountResponse fromDomain(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getInitialBalance(),
                account.getCurrentBalance(),
                account.getCreatedAt()
        );
    }
}
