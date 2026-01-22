package com.test.hex.cinehex.infrastructure.adapter.in.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;
import java.util.UUID;

public record MovieShowRequest(
    @NotNull(message = "movieId no puede ser nulo") UUID movieId,
    @NotNull(message = "El id de la sala no puede ser nulo") UUID roomId,
    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss", timezone = "UTC")
    @NotNull(message = "La fecha de inicio no puede ser nulo") Instant startTime,
    @NotNull(message = "El precio no puede ser nulo")
        @Pattern(
            message = "El precio debe tener un formato válido (ej: 15.99)",
            regexp = "^\\d+(\\.\\d{1,2})?$")
        String price) {}
