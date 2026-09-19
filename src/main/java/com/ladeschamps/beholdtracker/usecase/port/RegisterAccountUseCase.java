package com.ladeschamps.beholdtracker.usecase.port;

import com.ladeschamps.beholdtracker.domain.Account;

import java.math.BigDecimal;

/**
 * Inbound port for registering a new Account.
 */
public interface RegisterAccountUseCase {
    Account register(String name, BigDecimal initialBalance);
}
