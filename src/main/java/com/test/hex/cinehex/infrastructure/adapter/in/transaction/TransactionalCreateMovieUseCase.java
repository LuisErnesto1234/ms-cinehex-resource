package com.test.hex.cinehex.infrastructure.adapter.in.transaction;

import com.test.hex.cinehex.domain.model.Movie;
import com.test.hex.cinehex.domain.port.in.CreateMovieUseCase;

import com.test.hex.cinehex.domain.port.in.command.CreateMovieCommand;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Primary
@RequiredArgsConstructor
public class TransactionalCreateMovieUseCase implements CreateMovieUseCase {

    private final CreateMovieUseCase createMovieUseCase;

    @Transactional(rollbackFor = Exception.class, timeout = 5, propagation = Propagation.REQUIRED)
    @Override
    public Movie createMovie(CreateMovieCommand movie) {
        return createMovieUseCase.createMovie(movie);
    }
}
