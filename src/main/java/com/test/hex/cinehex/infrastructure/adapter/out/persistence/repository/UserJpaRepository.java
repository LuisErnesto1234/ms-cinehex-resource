package com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository;

import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<@NonNull UserEntity, @NonNull UUID> {}
