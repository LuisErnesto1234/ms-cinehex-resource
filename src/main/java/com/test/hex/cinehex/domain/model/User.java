package com.test.hex.cinehex.domain.model;

import java.util.UUID;

public record User(UUID id, String name, String lastName, String email) {
    public static User create(UUID id, String name, String lastName, String email) {
        return new User(id, name, lastName, email);
    }
}
