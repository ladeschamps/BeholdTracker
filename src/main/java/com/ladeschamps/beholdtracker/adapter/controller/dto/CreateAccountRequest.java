package com.ladeschamps.beholdtracker.adapter.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank(message = "Account name is mandatory")
        String name,

        @DecimalMin(value = "0.00", message = "Initial balance cannot be negative")
        BigDecimal initialBalance
) {
}
