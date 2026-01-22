package com.test.hex.cinehex.domain.port.in.command;

import java.util.UUID;

public record CreateUserCommand(UUID id, String name, String lastName, String email) {}
