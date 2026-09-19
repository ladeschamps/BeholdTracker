package com.ladeschamps.beholdtracker.usecase.port;

import com.ladeschamps.beholdtracker.domain.Account;

import java.math.BigDecimal;

/**
 * Inbound port for executing a cash deposit into an Account.
 */
public interface ExecuteDepositUseCase {
    Account execute(Long accountId, BigDecimal amount);
}
