package com.ladeschamps.beholdertracker.usecase.port;

import com.ladeschamps.beholdertracker.domain.Account;

import java.math.BigDecimal;

/**
 * Inbound port for executing a cash deposit into an existing account.
 */
public interface ExecuteDepositUseCase {

    Account execute(Long accountId, BigDecimal amount);
}
