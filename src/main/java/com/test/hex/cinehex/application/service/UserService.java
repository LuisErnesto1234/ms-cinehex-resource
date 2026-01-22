package com.test.hex.cinehex.application.service;

import com.test.hex.cinehex.domain.model.User;
import com.test.hex.cinehex.domain.port.in.CreateUserUseCase;
import com.test.hex.cinehex.domain.port.in.command.CreateUserCommand;
import com.test.hex.cinehex.domain.port.out.UserRepositoryPort;

/**
 * Servicio de Dominio - SIN dependencias de frameworks Esta clase contiene la lógica de negocio
 * pura
 */
public record UserService(UserRepositoryPort userRepositoryPort) implements CreateUserUseCase {

  @Override
  public User createUser(CreateUserCommand user) {

    User userToCreate = User.create(user.id(), user.name(), user.lastName(), user.email());

    validateUser(userToCreate);

    return userRepositoryPort.save(userToCreate);
  }

  private void validateUser(User user) {
    if (user == null || user.name() == null || user.name().isEmpty()) {
      throw new IllegalArgumentException("El nombre del usuario no puede estar vacío");
    }
  }
}
