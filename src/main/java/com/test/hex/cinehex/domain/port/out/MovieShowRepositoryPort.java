package com.test.hex.cinehex.domain.port.out;

import com.test.hex.cinehex.domain.model.MovieShow;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface MovieShowRepositoryPort {
  MovieShow save(MovieShow movieShow);

  Boolean existsById(UUID id);

  Boolean existsShowInRoomWithTimeOverlap(UUID roomId, Instant start, Instant end);

  Optional<MovieShow> findById(UUID movieShowId);
}
