package com.test.hex.cinehex.infrastructure.adapter.in.dto.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        String message,
        LocalDateTime timestamp
) {}
