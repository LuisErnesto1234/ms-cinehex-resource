package com.test.hex.cinehex.infrastructure.adapter.in.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record MovieRequest(
        @NotBlank(message = "Title must not be blank")
        @NotNull(message = "Title must not be null")
        @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Title must contain only alphanumeric characters and spaces")
        String title,
        @Positive(message = "Duration must be a positive integer")
        @NotNull(message = "Duration must not be null")
        Integer durationMinutes
) {}
