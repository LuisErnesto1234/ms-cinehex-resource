package com.test.hex.cinehex.domain.port.in;

import com.test.hex.cinehex.domain.model.details.MovieShowDetails;
import com.test.hex.cinehex.domain.port.in.command.CreateMovieShowCommand;

public interface CreateMovieShowUseCase {
    MovieShowDetails createMovieShow(CreateMovieShowCommand movieShow);
}
