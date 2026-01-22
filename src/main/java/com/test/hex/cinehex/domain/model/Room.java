package com.test.hex.cinehex.domain.model;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public record Room(UUID id, String name, Integer capacity) {

  public Room {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("El nombre de la sala no puede estar vacío");
    }
    if (capacity == null || capacity <= 0) {
      throw new IllegalArgumentException("La capacidad debe ser positiva");
    }
  }

  public static Room createNew(String name, Integer capacity) {

      log.info("Creando una nueva instancia de Room con nombre: {} y capacidad: {}", name, capacity);

    return new Room(UUID.randomUUID(), name, capacity);
  }
}
