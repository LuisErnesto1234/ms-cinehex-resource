package com.test.hex.cinehex.infrastructure.adapter;

import com.test.hex.cinehex.domain.model.User;
import com.test.hex.cinehex.domain.port.out.UserRepositoryPort;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper.UserEntityMapper;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

  private final UserJpaRepository userJpaRepository;
  private final UserEntityMapper userEntityMapper;

  @Override
  public User save(User user) {
    UserEntity userSaved = userEntityMapper.toEntity(user);
    return userEntityMapper.toDomain(userJpaRepository.save(userSaved));
  }

  @Override
  public Optional<User> findById(UUID userId) {
    return userJpaRepository.findById(userId).map(userEntityMapper::toDomain);
  }
}
