package com.test.hex.cinehex.domain.model;

import java.util.UUID;

public record Seat(UUID id, String row,
                   Integer number, UUID roomId) {

    public Seat {
        if (row == null || row.isBlank()) {
            throw new IllegalArgumentException("La fila no puede estar vacía");
        }
        if (number == null || number <= 0) {
            throw new IllegalArgumentException("El número del asiento debe ser positivo");
        }
        if (roomId == null) {
            throw new IllegalArgumentException("El ID de la sala no puede ser nulo");
        }
    }

    public static Seat createNew(String row, Integer number, UUID roomId) {
        return new Seat(UUID.randomUUID(), row, number, roomId);
    }

}
