package com.test.hex.cinehex.application.service;

import com.test.hex.cinehex.domain.model.Movie;
import com.test.hex.cinehex.domain.port.in.CreateMovieUseCase;
import com.test.hex.cinehex.domain.port.in.command.CreateMovieCommand;
import com.test.hex.cinehex.domain.port.out.MovieRepositoryPort;

/**
 * Servicio de Dominio - SIN dependencias de frameworks Esta clase contiene la lógica de negocio
 * pura
 */
public class MovieService implements CreateMovieUseCase {

  private final MovieRepositoryPort movieRepositoryPort;

  public MovieService(MovieRepositoryPort movieRepositoryPort) {
    this.movieRepositoryPort = movieRepositoryPort;
  }

  @Override
  public Movie createMovie(CreateMovieCommand command) {

    if (movieRepositoryPort.existsByTitle(command.title())) {
      throw new IllegalArgumentException("Ya existe una película con el título: " + command.title());
    }

    Movie movieToSave = Movie.createNew(command.title(), command.durationMinutes());

    return movieRepositoryPort.save(movieToSave);
  }
}
