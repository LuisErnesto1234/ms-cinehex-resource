package com.test.hex.cinehex.infrastructure.adapter.in.api.controller;

import com.test.hex.cinehex.domain.model.Movie;
import com.test.hex.cinehex.domain.port.in.CreateMovieUseCase;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.constant.ConstantUtil;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.MovieRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.ApiResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.MovieResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.mapper.MovieRestMapper;

import jakarta.validation.Valid;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ConstantUtil.API_V1_MOVIES)
@RequiredArgsConstructor
public class MovieController {

  private final CreateMovieUseCase createMovieUseCase;
  private final MovieRestMapper restMapper;

  @PostMapping
  public ResponseEntity<@NonNull ApiResponse<MovieResponse>> createMovie(
      @Valid @RequestBody MovieRequest request) {

      var command = restMapper.toCommand(request);

      Movie createdMovie = createMovieUseCase.createMovie(command);

      return ResponseEntity.status(HttpStatus.CREATED)
              .body(buildResponse(createdMovie));
  }

  private ApiResponse<MovieResponse> buildResponse(Movie movie) {
    return ApiResponse.<MovieResponse>builder()
        .code(HttpStatus.CREATED.value())
        .message(ConstantUtil.MOVIE_CREATED_SUCCESSFULLY)
        .data(restMapper.toResponse(movie)) // Mapeo de salida aquí
        .path(ConstantUtil.API_V1_MOVIES)
        .build();
  }
}
