package com.test.hex.cinehex.infrastructure.adapter.out.persistence.repository;

import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.MovieEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MovieJpaRepository extends JpaRepository<@NonNull MovieEntity, @NonNull UUID> {
    @Query(value = "SELECT m FROM MovieEntity m WHERE m.title = :title")
    Optional<MovieEntity> findMovieEntityByTitle(String title);

    @Query(value = "SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MovieEntity m WHERE m.title = :title")
    Boolean existsByTitle(String title);

}
