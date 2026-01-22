package com.test.hex.cinehex.domain.port.out;

import com.test.hex.cinehex.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(UUID userId);
}
