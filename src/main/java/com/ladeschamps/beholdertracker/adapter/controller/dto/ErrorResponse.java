package com.ladeschamps.beholdertracker.adapter.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standardized JSON error response payload.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> details
) {
}
