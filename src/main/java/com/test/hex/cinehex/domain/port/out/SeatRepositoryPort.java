package com.test.hex.cinehex.domain.port.out;

import com.test.hex.cinehex.domain.model.Seat;

import java.util.List;
import java.util.UUID;

public interface SeatRepositoryPort {

  Seat save(Seat seat);

  Boolean existsByRowAndNumberAndRoomId(String row, Integer number, UUID roomId);

  List<Seat> findAllByIdIn(List<UUID> seatsIds);
}
