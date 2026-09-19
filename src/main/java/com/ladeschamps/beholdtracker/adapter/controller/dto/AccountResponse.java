package com.ladeschamps.beholdtracker.adapter.controller.dto;

import com.ladeschamps.beholdtracker.domain.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
