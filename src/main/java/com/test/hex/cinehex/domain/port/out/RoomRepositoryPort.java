package com.test.hex.cinehex.domain.port.out;

import com.test.hex.cinehex.domain.model.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepositoryPort {
    Room save(Room room);
    Boolean existsByTitle(String title);
    Boolean existsById(UUID id);
    Optional<Room> findById(UUID id);
}
