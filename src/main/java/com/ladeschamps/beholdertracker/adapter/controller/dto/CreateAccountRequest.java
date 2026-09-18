package com.ladeschamps.beholdertracker.adapter.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * Request payload for creating a new account.
 */
public record CreateAccountRequest(
        @NotBlank(message = "Account name is required")
        String name,

        @NotNull(message = "Initial balance is required")
        @PositiveOrZero(message = "Initial balance must be zero or positive")
        BigDecimal initialBalance
) {
}
