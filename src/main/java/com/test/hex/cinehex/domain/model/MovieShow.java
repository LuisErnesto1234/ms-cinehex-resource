package com.test.hex.cinehex.domain.model;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
public record MovieShow(
    UUID id, UUID movieId, UUID roomId, Instant startTime, Instant endTime, BigDecimal price) {

  public MovieShow {

    log.info(
        "Validating MovieShow data: movieId={}, roomId={}, startTime={}, endTime={}, price={}",
        movieId,
        roomId,
        startTime,
        endTime,
        price);

    if (movieId == null) {
      throw new IllegalArgumentException("movieId no puede ser nulo");
    }

    if (roomId == null) {
      throw new IllegalArgumentException("roomId no puede ser nulo");
    }

    if (startTime == null) {
      throw new IllegalArgumentException("startTime no puede ser nulo");
    }

    if (endTime == null) {
      throw new IllegalArgumentException("endTime no puede ser nulo");
    }

    if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("price no puede ser nulo o negativo");
    }
  }

  public static MovieShow createNew(
      UUID movieId, UUID roomId, Instant startTime, Instant endTime, BigDecimal price) {
    return new MovieShow(UUID.randomUUID(), movieId, roomId, startTime, endTime, price);
  }
}
