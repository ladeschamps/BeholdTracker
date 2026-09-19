package com.ladeschamps.beholdtracker.usecase.port;

import com.ladeschamps.beholdtracker.domain.Account;

import java.math.BigDecimal;

/**
 * Inbound port for executing a cash withdrawal from an Account.
 */
public interface ExecuteWithdrawalUseCase {
    Account execute(Long accountId, BigDecimal amount);
}
