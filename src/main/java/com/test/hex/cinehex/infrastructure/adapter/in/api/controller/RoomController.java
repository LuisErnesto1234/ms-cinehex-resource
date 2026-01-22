package com.test.hex.cinehex.infrastructure.adapter.in.api.controller;

import com.test.hex.cinehex.domain.model.Room;
import com.test.hex.cinehex.domain.port.in.CreateRoomUseCase;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.RoomRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.ApiResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.RoomResponse;
import com.test.hex.cinehex.infrastructure.adapter.in.mapper.RoomRestMapper;

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
@RequestMapping(value = "/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final CreateRoomUseCase createRoomUseCase;
    private final RoomRestMapper restMapper;

    @PostMapping
    public ResponseEntity<@NonNull ApiResponse<RoomResponse>> createRoom(@Valid @RequestBody RoomRequest request){

        var command = restMapper.toCommand(request);

        var createdRoom = createRoomUseCase.createRoom(command);

        return ResponseEntity.status(201)
                .body(buildResponse(createdRoom));
    }

    private @NonNull ApiResponse<RoomResponse> buildResponse(@NonNull Room room){
        var roomResponse = restMapper.toResponse(room);

        return ApiResponse.<RoomResponse>builder()
                .data(roomResponse)
                .code(HttpStatus.CREATED.value())
                .message("Room created successfully")
                .path("/api/v1/rooms")
                .build();
    }

}
