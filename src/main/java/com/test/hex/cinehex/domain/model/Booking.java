package com.test.hex.cinehex.domain.model;

import com.test.hex.cinehex.domain.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Booking(
    UUID id,
    UUID userId,
    UUID movieShowId,
    List<UUID> seatsIds,
    BigDecimal totalAmount,
    BookingStatus bookingStatus,
    Instant createdAt) {

  public Booking {
    if (seatsIds == null || seatsIds.isEmpty()) {
      throw new IllegalArgumentException("Debe haber al menos un asiento a reservar");
    }
    if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("El monto total debe ser positivo");
    }
    if (bookingStatus == null) {
      throw new IllegalArgumentException("El estado de la reserva no puede ser nulo");
    }
    if (createdAt == null) {
      throw new IllegalArgumentException("La fecha de creación no puede ser nula");
    }
  }

  public static Booking createNew(
      UUID userId, UUID movieShowId, List<UUID> seatsIds, BigDecimal totalAmount) {
    return new Booking(
        UUID.randomUUID(),
        userId,
        movieShowId,
        seatsIds,
        totalAmount,
        BookingStatus.PENDING,
        Instant.now());
  }

  public Booking confirm() {
    if (this.bookingStatus != BookingStatus.PENDING) {
      throw new IllegalStateException("No se puede confirmar una reserva que no está pendiente.");
    }

    return new Booking(
        this.id,
        this.userId,
        this.movieShowId,
        this.seatsIds,
        this.totalAmount,
        BookingStatus.CONFIRMED, // <--- El cambio aquí
        this.createdAt);
  }

  public Booking expire() {
    // Retornamos una NUEVA instancia con estado EXPIRED
    return new Booking(
        this.id,
        this.userId,
        this.movieShowId,
        this.seatsIds,
        this.totalAmount,
        BookingStatus.EXPIRED, // <--- El cambio aquí
        this.createdAt);
  }
}
