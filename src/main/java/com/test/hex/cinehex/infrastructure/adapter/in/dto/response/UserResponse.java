package com.test.hex.cinehex.infrastructure.adapter.in.dto.response;

import java.util.UUID;

public record UserResponse(UUID id, String name, String lastName, String email) {}
