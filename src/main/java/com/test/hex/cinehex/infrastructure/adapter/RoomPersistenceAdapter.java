package com.test.hex.cinehex.infrastructure.adapter;

import com.test.hex.cinehex.domain.model.Room;
import com.test.hex.cinehex.domain.port.out.RoomRepositoryPort;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.RoomEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper.RoomEntityMapper;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.RoomJpaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoomPersistenceAdapter implements RoomRepositoryPort {

  private final RoomJpaRepository roomJpaRepository;
  private final RoomEntityMapper roomEntityMapper;

  @Override
  public Room save(Room room) {
    RoomEntity roomEntity = roomEntityMapper.toEntity(room);

    return roomEntityMapper.toDomain(roomJpaRepository.save(roomEntity));
  }

  @Override
  public Boolean existsByTitle(String title) {
    return roomJpaRepository.existsByName(title);
  }

  @Override
  public Boolean existsById(UUID id) {
    return roomJpaRepository.existsById(id);
  }

  @Override
  public Optional<Room> findById(UUID id) {
    return roomJpaRepository.findById(id)
        .map(roomEntityMapper::toDomain);
  }
}
