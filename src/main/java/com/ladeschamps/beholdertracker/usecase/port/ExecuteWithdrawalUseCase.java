package com.ladeschamps.beholdertracker.usecase.port;

import com.ladeschamps.beholdertracker.domain.Account;

import java.math.BigDecimal;

/**
 * Inbound port for executing a cash withdrawal from an existing account.
 */
public interface ExecuteWithdrawalUseCase {

    Account execute(Long accountId, BigDecimal amount);
}
