package com.test.hex.cinehex.domain.port.in;

import com.test.hex.cinehex.domain.model.Movie;
import com.test.hex.cinehex.domain.port.in.command.CreateMovieCommand;

public interface CreateMovieUseCase {
    Movie createMovie(CreateMovieCommand movie);
}
