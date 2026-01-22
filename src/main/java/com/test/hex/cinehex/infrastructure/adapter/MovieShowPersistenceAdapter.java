package com.test.hex.cinehex.infrastructure.adapter;

import com.test.hex.cinehex.domain.model.MovieShow;
import com.test.hex.cinehex.domain.port.out.MovieShowRepositoryPort;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.MovieEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.MovieShowEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.RoomEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper.MovieShowEntityMapper;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.MovieJpaRepository;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.MovieShowJpaRepository;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.RoomJpaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MovieShowPersistenceAdapter implements MovieShowRepositoryPort {

  private final MovieShowJpaRepository movieShowJpaRepository;
  private final MovieJpaRepository movieJpaRepository;
  private final RoomJpaRepository roomJpaRepository;
  private final MovieShowEntityMapper movieShowEntityMapper;

  @Override
  public MovieShow save(MovieShow movieShow) {

    var movieEntity = getMovieEntity(movieShow.movieId());
    var roomEntity = getRoomEntity(movieShow.roomId());

    MovieShowEntity movieShowEntity =
        movieShowEntityMapper.toEntity(movieShow, roomEntity, movieEntity);

    return movieShowEntityMapper.toDomain(movieShowJpaRepository.save(movieShowEntity));
  }

  private MovieEntity getMovieEntity(UUID movieId) {
    return movieJpaRepository
        .findById(movieId)
        .orElseThrow(
            () -> new IllegalArgumentException("La película con ID " + movieId + " no existe."));
  }

  private RoomEntity getRoomEntity(UUID roomId) {
    return roomJpaRepository
        .findById(roomId)
        .orElseThrow(
            () -> new IllegalArgumentException("La sala con ID " + roomId + " no existe."));
  }

  @Override
  public Boolean existsById(UUID id) {
    return movieShowJpaRepository.existsById(id);
  }

  @Override
  public Boolean existsShowInRoomWithTimeOverlap(UUID roomId, Instant start, Instant end) {
    return movieShowJpaRepository.existsShowInRoomWithTimeOverlap(roomId, start, end);
  }

  @Override
  public Optional<MovieShow> findById(UUID movieShowId) {
    return movieShowJpaRepository.findById(movieShowId).map(movieShowEntityMapper::toDomain);
  }
}
