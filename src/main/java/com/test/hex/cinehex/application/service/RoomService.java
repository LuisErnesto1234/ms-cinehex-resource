package com.test.hex.cinehex.application.service;

import com.test.hex.cinehex.domain.model.Room;
import com.test.hex.cinehex.domain.port.in.CreateRoomUseCase;
import com.test.hex.cinehex.domain.port.in.command.CreateRoomCommand;
import com.test.hex.cinehex.domain.port.out.RoomRepositoryPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoomService implements CreateRoomUseCase {

  private final RoomRepositoryPort roomRepositoryPort;

  public RoomService(RoomRepositoryPort roomRepositoryPort) {
    this.roomRepositoryPort = roomRepositoryPort;
  }

  @Override
  public Room createRoom(CreateRoomCommand command) {

    if (Boolean.TRUE.equals(roomRepositoryPort.existsByTitle(command.name()))) {
      throw new IllegalArgumentException("Ya existe una sala con el título: " + command.name());
    }

    log.info(
        "Creando una nueva sala con el nombre: {} y capacidad: {}",
        command.name(),
        command.capacity());

    Room roomToSave = Room.createNew(command.name(), command.capacity());

    return roomRepositoryPort.save(roomToSave);
  }
}
