package com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository;

import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.RoomEntity;

import lombok.NonNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomJpaRepository extends JpaRepository<@NonNull RoomEntity, @NonNull UUID> {
    Boolean existsByName(@NonNull String title);
}
