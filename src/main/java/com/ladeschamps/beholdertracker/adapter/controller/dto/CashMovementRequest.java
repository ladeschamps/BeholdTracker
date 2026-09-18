package com.ladeschamps.beholdertracker.adapter.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Request payload for cash movement operations (deposit / withdraw).
 */
public record CashMovementRequest(
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be strictly positive")
        BigDecimal amount
) {
}
