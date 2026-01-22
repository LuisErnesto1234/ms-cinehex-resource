package com.test.hex.cinehex.application.service;

import com.test.hex.cinehex.domain.model.Room;
import com.test.hex.cinehex.domain.model.Seat;
import com.test.hex.cinehex.domain.model.details.SeatDetails;
import com.test.hex.cinehex.domain.port.in.CreateSeatUseCase;
import com.test.hex.cinehex.domain.port.in.command.CreateSeatCommand;
import com.test.hex.cinehex.domain.port.out.RoomRepositoryPort;
import com.test.hex.cinehex.domain.port.out.SeatRepositoryPort;

import java.util.Optional;

public class SeatService implements CreateSeatUseCase {

  private final SeatRepositoryPort seatRepositoryPort;
  private final RoomRepositoryPort roomRepositoryPort;

  public SeatService(SeatRepositoryPort seatRepositoryPort, RoomRepositoryPort roomRepositoryPort) {
    this.seatRepositoryPort = seatRepositoryPort;
    this.roomRepositoryPort = roomRepositoryPort;
  }

  @Override
  public SeatDetails createSeat(CreateSeatCommand command) {

    validateData(command);

    // Validar que la sala existe
    Optional<Room> room = roomRepositoryPort.findById(command.roomId());

    if (room.isEmpty()) {
      throw new IllegalArgumentException("La sala con ID " + command.roomId() + " no existe.");
    }

    Seat seatToSave = Seat.createNew(command.row(), command.number(), command.roomId());

    Seat savedSeat = seatRepositoryPort.save(seatToSave);

    return new SeatDetails(savedSeat.id(), savedSeat.row(), savedSeat.number(), room.get());
  }

  private void validateData(CreateSeatCommand command) {
    if (command.row() == null || command.row().isBlank()) {
      throw new IllegalArgumentException("La fila del asiento no puede ser nula.");
    }
    if (command.number() <= 0) {
      throw new IllegalArgumentException("El número del asiento debe ser un número positivo.");
    }
  }
}
