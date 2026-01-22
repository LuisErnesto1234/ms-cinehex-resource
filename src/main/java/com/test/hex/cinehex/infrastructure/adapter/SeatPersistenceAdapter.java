package com.test.hex.cinehex.infrastructure.adapter;

import com.test.hex.cinehex.domain.model.Seat;
import com.test.hex.cinehex.domain.port.out.SeatRepositoryPort;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.RoomEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.SeatEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper.SeatEntityMapper;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.RoomJpaRepository;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.SeatJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeatPersistenceAdapter implements SeatRepositoryPort {

  private final SeatJpaRepository seatJpaRepository;
  private final SeatEntityMapper seatEntityMapper;
  private final RoomJpaRepository roomJpaRepository;

  @Override
  public Seat save(Seat seat) {
    RoomEntity room =
        roomJpaRepository
            .findById(seat.roomId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "La sala con ID " + seat.roomId() + " no existe."));

    SeatEntity seatEntity = seatEntityMapper.toEntity(seat, room);

    log.info("Saving seat entity: {}", seatEntity);

    return seatEntityMapper.toDomain(seatJpaRepository.save(seatEntity));
  }

  @Override
  public Boolean existsByRowAndNumberAndRoomId(String row, Integer number, UUID roomId) {
    return seatJpaRepository.existsByRowAndNumberAndRoomId(row, number, roomId);
  }

  @Override
  public List<Seat> findAllByIdIn(List<UUID> seatsIds) {
    return seatJpaRepository.findAllById(seatsIds).stream()
        .map(seatEntityMapper::toDomain)
        .toList();
  }
}
