package com.test.hex.cinehex.infrastructure.adapter.in.api.controller;

import com.test.hex.cinehex.domain.port.in.CreateMovieShowUseCase;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.MovieShowRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.ApiResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.MovieShowResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.mapper.MovieShowRestMapper;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/movie-shows")
@RequiredArgsConstructor
public class MovieShowController {

  private final CreateMovieShowUseCase createMovieShowUseCase;
  private final MovieShowRestMapper movieShowRestMapper;

  @PostMapping
  public ResponseEntity<@NonNull ApiResponse<MovieShowResponse>> createMovieShow(
      @Valid @RequestBody MovieShowRequest request) {

    var command = movieShowRestMapper.toCommand(request);

    var movieShow = createMovieShowUseCase.createMovieShow(command);

    var response = movieShowRestMapper.toResponse(movieShow);

    return buildResponseEntity(response);
  }

  private ResponseEntity<@NonNull ApiResponse<MovieShowResponse>> buildResponseEntity(
      MovieShowResponse movieShowResponse) {
    var apiResponse =
        ApiResponse.<MovieShowResponse>builder()
            .data(movieShowResponse)
            .message("Movie Show created successfully")
            .path("/api/v1/movie-shows")
            .code(HttpStatus.CREATED.value())
            .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
  }
}
