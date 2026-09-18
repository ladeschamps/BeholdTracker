package com.ladeschamps.beholdertracker.usecase.port;

import com.ladeschamps.beholdertracker.domain.Account;

import java.math.BigDecimal;

/**
 * Inbound port for registering a new brokerage or cash account.
 */
public interface RegisterAccountUseCase {

    Account register(String name, BigDecimal initialBalance);
}
