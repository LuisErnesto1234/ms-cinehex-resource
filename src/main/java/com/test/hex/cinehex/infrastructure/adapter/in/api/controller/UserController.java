package com.test.hex.cinehex.infrastructure.adapter.in.api.controller;

import com.test.hex.cinehex.domain.model.User;
import com.test.hex.cinehex.domain.port.in.CreateUserUseCase;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.constant.ConstantUtil;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.UserRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.ApiResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.UserResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.mapper.UserRestMapper;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final CreateUserUseCase createUserUseCase;
  private final UserRestMapper userRestMapper;

  @PostMapping
  public ResponseEntity<@NonNull ApiResponse<UserResponse>> createUser(
      @Valid @RequestBody UserRequest userRequest) {

    var userCommand = userRestMapper.toCommand(userRequest);

    var createdUser = createUserUseCase.createUser(userCommand);

    return ResponseEntity.status(HttpStatus.CREATED).body(buildResponse(createdUser));
  }

  private ApiResponse<UserResponse> buildResponse(User user) {
    return ApiResponse.<UserResponse>builder()
        .code(HttpStatus.CREATED.value())
        .message(ConstantUtil.MOVIE_CREATED_SUCCESSFULLY)
        .data(userRestMapper.toResponse(user)) // Mapeo de salida aquí
        .path(ConstantUtil.API_V1_MOVIES)
        .build();
  }
}
