package com.test.hex.cinehex.application.service;

import com.test.hex.cinehex.domain.model.Movie;
import com.test.hex.cinehex.domain.model.MovieShow;
import com.test.hex.cinehex.domain.model.Room;
import com.test.hex.cinehex.domain.model.details.MovieShowDetails;
import com.test.hex.cinehex.domain.port.in.CreateMovieShowUseCase;
import com.test.hex.cinehex.domain.port.in.command.CreateMovieShowCommand;
import com.test.hex.cinehex.domain.port.out.MovieRepositoryPort;
import com.test.hex.cinehex.domain.port.out.MovieShowRepositoryPort;
import com.test.hex.cinehex.domain.port.out.RoomRepositoryPort;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class MovieShowService implements CreateMovieShowUseCase {

  private final MovieShowRepositoryPort movieShowRepositoryPort;
  private final RoomRepositoryPort roomRepositoryPort;
  private final MovieRepositoryPort movieRepositoryPort;

  public MovieShowService(
      MovieShowRepositoryPort movieShowRepositoryPort,
      RoomRepositoryPort roomRepositoryPort,
      MovieRepositoryPort movieRepositoryPort) {
    this.movieShowRepositoryPort = movieShowRepositoryPort;
    this.roomRepositoryPort = roomRepositoryPort;
    this.movieRepositoryPort = movieRepositoryPort;
  }

  @Override
  public MovieShowDetails createMovieShow(CreateMovieShowCommand command) {
    // 1. Validar existencia de Sala (Rápido)
    validateRoomExists(command.roomId());
    // 2. Obtener Película (Necesario para saber la duración)
    Movie movie = fetchMovieById(command.movieId());
    Room room =
        roomRepositoryPort
            .findById(command.roomId())
            .orElseThrow(() -> new IllegalArgumentException("La sala no existe"));

    // 3. Calcular Horario Real
    Instant startTime = command.startTime();
    Instant endTime = startTime.plus(movie.durationMinutes(), ChronoUnit.MINUTES);

    // 4. VALIDACIÓN DE SOLAPAMIENTO (Delegada a la Infraestructura)
    // Aquí solucionamos el Bug Lógico y el Problema de Rendimiento de un golpe.
    boolean isOccupied =
        movieShowRepositoryPort.existsShowInRoomWithTimeOverlap(
            command.roomId(), startTime, endTime);

    log.info("Esta ocupado la sala? {}", isOccupied);

    if (isOccupied) {
      throw new IllegalArgumentException("La sala no está disponible en el horario solicitado");
    }

    log.info("El comando que me llego, {}", command);

    // 5. Crear Modelo (Ahora el modelo debería recibir también el endTime si decidiste guardarlo)
    MovieShow movieShowModel =
        MovieShow.createNew(
            command.movieId(),
            command.roomId(),
            startTime,
            endTime, // Recomendación: Guarda esto en BD
            command.price());

    MovieShow movieShowSaved = movieShowRepositoryPort.save(movieShowModel);

    return new MovieShowDetails(
        movieShowSaved.id(), movie, room, startTime, endTime, command.price());
  }

  private void validateRoomExists(UUID roomId) {
    boolean roomExists = roomRepositoryPort.existsById(roomId);
    if (!roomExists) {
      throw new IllegalArgumentException("La sala no existe");
    }
  }

  private Movie fetchMovieById(UUID movieId) {
    Optional<Movie> movieOpt = movieRepositoryPort.findById(movieId);
    return movieOpt.orElseThrow(() -> new IllegalArgumentException("La película no existe"));
  }
}
