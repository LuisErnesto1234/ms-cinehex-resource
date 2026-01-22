package com.test.hex.cinehex.domain.port.out;

import com.test.hex.cinehex.domain.model.Movie;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepositoryPort {
    Movie save(Movie movie);
    Boolean existsByTitle(String title);
    Optional<Movie> findById(UUID id);
}
