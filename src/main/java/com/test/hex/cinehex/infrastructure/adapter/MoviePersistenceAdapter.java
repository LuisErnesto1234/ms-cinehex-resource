package com.test.hex.cinehex.infrastructure.adapter;

import com.test.hex.cinehex.domain.model.Movie;
import com.test.hex.cinehex.domain.port.out.MovieRepositoryPort;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.MovieEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper.MovieEntityMapper;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.MovieJpaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MoviePersistenceAdapter implements MovieRepositoryPort {

  private final MovieJpaRepository movieJpaRepository;
  private final MovieEntityMapper movieEntityMapper;

  @Override
  public Movie save(Movie movie) {
    MovieEntity movieEntity = movieEntityMapper.toEntity(movie);
    return movieEntityMapper.toDomain(movieJpaRepository.save(movieEntity));
  }

  @Override
  public Boolean existsByTitle(String title) {
    return movieJpaRepository.existsByTitle(title);
  }

  @Override
  public Optional<Movie> findById(UUID id) {
    return movieJpaRepository.findById(id).map(movieEntityMapper::toDomain);
  }
}
