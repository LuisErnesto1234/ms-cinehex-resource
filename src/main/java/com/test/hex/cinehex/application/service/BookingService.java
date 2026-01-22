package com.test.hex.cinehex.application.service;

import com.test.hex.cinehex.domain.enums.BookingStatus;
import com.test.hex.cinehex.domain.model.Booking;
import com.test.hex.cinehex.domain.model.details.BookingReceipt;
import com.test.hex.cinehex.domain.model.Seat;
import com.test.hex.cinehex.domain.port.in.ConfirmBookingUseCase;
import com.test.hex.cinehex.domain.port.in.CreateBookingUseCase;
import com.test.hex.cinehex.domain.port.in.command.ConfirmBookingCommand;
import com.test.hex.cinehex.domain.port.in.command.CreateBookingCommand;
import com.test.hex.cinehex.domain.port.out.BookingRepositoryPort;
import com.test.hex.cinehex.domain.port.out.MovieShowRepositoryPort;
import com.test.hex.cinehex.domain.port.out.SeatRepositoryPort;
import com.test.hex.cinehex.domain.port.out.UserRepositoryPort;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BookingService implements CreateBookingUseCase, ConfirmBookingUseCase {

  private final BookingRepositoryPort bookingRepositoryPort;
  private final UserRepositoryPort userRepositoryPort;
  private final MovieShowRepositoryPort movieShowRepositoryPort;
  private final SeatRepositoryPort seatRepositoryPort;

  public BookingService(
      BookingRepositoryPort bookingRepositoryPort,
      UserRepositoryPort userRepositoryPort,
      MovieShowRepositoryPort movieShowRepositoryPort,
      SeatRepositoryPort seatRepositoryPort) {
    this.bookingRepositoryPort = bookingRepositoryPort;
    this.userRepositoryPort = userRepositoryPort;
    this.movieShowRepositoryPort = movieShowRepositoryPort;
    this.seatRepositoryPort = seatRepositoryPort;
  }

  @Override
  public BookingReceipt createBooking(CreateBookingCommand command) {

    // 1. Validaciones básicas (Usuario, Función)
    var userExists =
        userRepositoryPort
            .findById(command.userId())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    var movieShow =
        movieShowRepositoryPort
            .findById(command.movieShowId())
            .orElseThrow(() -> new IllegalArgumentException("Función no encontrada"));

    // 2. VALIDACIÓN DE DISPONIBILIDAD (Corregida)
    validateSeatAvailability(command.movieShowId(), command.seatsIds());

    // 3. Crear Modelo
    BigDecimal totalAmount =
        movieShow.price().multiply(BigDecimal.valueOf(command.seatsIds().size()));

    Booking booking =
        Booking.createNew(userExists.id(), movieShow.id(), command.seatsIds(), totalAmount);

    Booking savedBooking = bookingRepositoryPort.save(booking);

    List<Seat> seats = seatRepositoryPort.findAllByIdIn(command.seatsIds());

    return new BookingReceipt(savedBooking, userExists, movieShow, seats);
  }

  private void validateSeatAvailability(UUID movieShowId, List<UUID> seatsIds) {

    if (seatsIds.isEmpty() || seatsIds.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "La lista de asientos no puede estar vacía o contener valores nulos.");
    }

    if (movieShowId == null) {
      throw new IllegalArgumentException("El ID de la función no puede ser nulo.");
    }

    // Preguntamos al repositorio de RESERVAS si hay conflicto
    List<UUID> occupiedSeats = bookingRepositoryPort.findOccupiedSeats(movieShowId, seatsIds);

    if (!occupiedSeats.isEmpty()) {
      // Si la lista no está vacía, significa que ALGUIEN nos ganó el asiento
      throw new IllegalArgumentException(
          "Los siguientes asientos ya están ocupados: " + occupiedSeats);
    }
  }

  @Override
  public BookingReceipt confirmBooking(ConfirmBookingCommand command) {

    var bookingExists =
        bookingRepositoryPort
            .findById(command.bookingId())
            .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

    var userExists =
        userRepositoryPort
            .findById(bookingExists.userId())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    var movieShow =
        movieShowRepositoryPort
            .findById(bookingExists.movieShowId())
            .orElseThrow(() -> new IllegalArgumentException("Función no encontrada"));

    if (bookingExists.bookingStatus() != BookingStatus.PENDING) {
      throw new IllegalArgumentException("La reserva no está en estado pendiente");
    }

    Instant expirationTime = bookingExists.createdAt().plus(15, ChronoUnit.MINUTES);

    if (Instant.now().isAfter(expirationTime)) {
      // Ups, llegaste tarde. Marcamos como expirada y rechazamos el pago.
      bookingExists = bookingExists.expire();
      bookingRepositoryPort.save(bookingExists);
      throw new IllegalArgumentException("La reserva ha expirado");
    }

    List<Seat> seats = seatRepositoryPort.findAllByIdIn(bookingExists.seatsIds());

    // Aquí iría la lógica de integración con el sistema de pagos
    bookingExists = bookingExists.confirm();
    Booking savedBooking = bookingRepositoryPort.save(bookingExists);

    return new BookingReceipt(savedBooking, userExists, movieShow, seats);
  }
}
