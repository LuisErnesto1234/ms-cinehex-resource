package com.test.hex.cinehex.infrastructure.adapter.in.transaction;

import com.test.hex.cinehex.domain.model.details.MovieShowDetails;
import com.test.hex.cinehex.domain.port.in.CreateMovieShowUseCase;
import com.test.hex.cinehex.domain.port.in.command.CreateMovieShowCommand;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Primary
@RequiredArgsConstructor
public class TransactionalCreateMovieShowUseCase implements CreateMovieShowUseCase {

  private final CreateMovieShowUseCase createMovieShowUseCase;

  @Transactional(rollbackFor = Exception.class, timeout = 5, propagation = Propagation.REQUIRED)
  @Override
  public MovieShowDetails createMovieShow(CreateMovieShowCommand movieShow) {
    return createMovieShowUseCase.createMovieShow(movieShow);
  }
}
