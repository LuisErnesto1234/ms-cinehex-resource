package com.test.hex.cinehex.infrastructure.adapter.in.api.controller;

import com.test.hex.cinehex.domain.port.in.ConfirmBookingUseCase;
import com.test.hex.cinehex.domain.port.in.CreateBookingUseCase;
import com.test.hex.cinehex.domain.port.in.command.ConfirmBookingCommand;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.BookingRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.ApiResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.BookingResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.mapper.BookingRestMapper;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

  private final CreateBookingUseCase createBookingUseCase;
  private final ConfirmBookingUseCase confirmBookingUseCase;
  private final BookingRestMapper bookingRestMapper;

  @PostMapping
  public ResponseEntity<@NonNull ApiResponse<BookingResponse>> createBooking(
      @Valid @RequestBody BookingRequest request, @AuthenticationPrincipal Jwt jwt) {

      // 1. Extraer el UUID del usuario desde el Token
      String userIdFromToken = jwt.getSubject(); // "sub": "uuid-123..."

      // 2. Seguridad Extra: ¿El usuario del token es el mismo que viene en el request?
      // Esto evita que yo (User A) haga una reserva a nombre de (User B)
      if (!request.userId().toString().equals(userIdFromToken)) {
          throw new AccessDeniedException("No puedes crear reservas para otros usuarios");
      }

    var command = bookingRestMapper.toCommand(request);

    var receipt = createBookingUseCase.createBooking(command);

    var response = bookingRestMapper.toResponse(receipt);

    return buildResponseEntity(response);
  }

  private ResponseEntity<@NonNull ApiResponse<BookingResponse>> buildResponseEntity(
      BookingResponse bookingResponse) {

    ApiResponse<BookingResponse> apiResponse =
        ApiResponse.<BookingResponse>builder()
            .code(HttpStatus.CREATED.value())
            .path("/api/v1/bookings")
            .message("Booking created successfully")
            .data(bookingResponse)
            .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
  }

  @PostMapping("/{id}/confirm")
  public ResponseEntity<@NonNull ApiResponse<BookingResponse>> confirmBooking(
      @PathVariable(name = "id") UUID id) {

    var command = new ConfirmBookingCommand(id, "dummy-token");

    var receipt = confirmBookingUseCase.confirmBooking(command);

    var response = bookingRestMapper.toResponse(receipt);

    return buildResponseEntity(response);
  }
}
