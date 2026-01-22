package com.test.hex.cinehex.domain.port.in.command;

import java.util.UUID;

public record CreateSeatCommand(String row, Integer number, UUID roomId) {}
