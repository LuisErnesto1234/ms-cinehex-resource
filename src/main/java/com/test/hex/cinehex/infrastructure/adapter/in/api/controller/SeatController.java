package com.test.hex.cinehex.infrastructure.adapter.in.api.controller;

import com.test.hex.cinehex.domain.port.in.CreateSeatUseCase;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.SeatRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.ApiResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.SeatResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.mapper.SeatRestMapper;

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
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

  private final CreateSeatUseCase createSeatUseCase;
  private final SeatRestMapper seatMapper;

  @PostMapping
  public ResponseEntity<@NonNull ApiResponse<SeatResponse>> createSeat(
      @Valid @RequestBody SeatRequest seatRequest) {

    var createSeatCommand = seatMapper.toCommand(seatRequest);

    var seatDetails = createSeatUseCase.createSeat(createSeatCommand);

    var response = seatMapper.toResponse(seatDetails);

    return buildResponseEntity(response);
  }

  private ResponseEntity<@NonNull ApiResponse<SeatResponse>> buildResponseEntity(
      SeatResponse seatResponse) {
    var apiResponse =
        ApiResponse.<SeatResponse>builder()
            .data(seatResponse)
            .message("Seat created successfully")
            .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
  }
}
