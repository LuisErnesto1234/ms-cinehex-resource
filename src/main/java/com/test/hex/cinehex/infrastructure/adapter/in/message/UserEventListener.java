package com.test.hex.cinehex.infrastructure.adapter.in.message;

import com.test.hex.cinehex.domain.model.User;
import com.test.hex.cinehex.domain.port.in.CreateUserUseCase;
import com.test.hex.cinehex.domain.port.in.command.CreateUserCommand;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.events.UserRegisteredEvent;
import com.test.hex.cinehex.infrastructure.config.RabbitConsumerConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {

  private final CreateUserUseCase createUserUseCase;

  @RabbitListener(queues = RabbitConsumerConfig.QUEUE_NAME)
  public void receptUserRegistrationEvent(UserRegisteredEvent event) {

    log.info("📨 RabbitMQ Nativo: Recibido usuario {}", event.email());

    var command = new CreateUserCommand(event.userId(), "Luis", "Gomez", event.email());

    log.info("El id del comando es: {}", command.id());

    try {
      User userCreated = createUserUseCase.createUser(command);
      log.info("Usuario creado en el sistema de recursos, ID: {}", userCreated.id());
    } catch (Exception e) {
      log.error("Error procesando evento", e);
      // Al lanzar la excepción, RabbitMQ reintenta o manda a Dead Letter (según config)
      throw e;
    }
  }
}
