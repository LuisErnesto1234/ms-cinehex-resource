package com.test.hex.cinehex.infrastructure.adapter.in.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record SeatRequest(
        @NotNull(message = "La fila no puede ser nula")
        @NotBlank(message = "La fila no puede estar vacía")
        String row,
        @Positive(message = "El número del asiento debe ser positivo")
        @NotNull(message = "El número del asiento no puede ser nulo")
        Integer number,
        @NotNull(message = "El ID de la sala no puede ser nulo")
        UUID roomId) {}
