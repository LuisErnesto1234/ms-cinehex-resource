package com.test.hex.cinehex.domain.port.in;

import com.test.hex.cinehex.domain.model.User;
import com.test.hex.cinehex.domain.port.in.command.CreateUserCommand;

public interface CreateUserUseCase {
    User createUser(CreateUserCommand command);
}
