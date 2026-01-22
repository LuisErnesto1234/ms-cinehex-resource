package com.test.hex.cinehex.domain.model;

import java.util.UUID;

public record Movie(UUID id, String title, Integer durationMinutes) {

    // Constructor Compacto: Se ejecuta SIEMPRE que alguien intente crear una Movie
    public Movie {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (durationMinutes == null || durationMinutes <= 0) {
            throw new IllegalArgumentException("La duración debe ser positiva");
        }
        // Si pasa esto, Java asigna los valores automáticamente
    }

    // Opcional: Factory Method si quieres generar el UUID tú mismo
    public static Movie createNew(String title, Integer durationMinutes) {
        return new Movie(UUID.randomUUID(), title, durationMinutes);
    }
}
