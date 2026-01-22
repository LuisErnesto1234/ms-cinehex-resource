package com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper;

import com.test.hex.cinehex.domain.model.MovieShow;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.MovieEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.MovieShowEntity;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.RoomEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieShowEntityMapper {

  @Mapping(target = "id", source = "movieShowEntity.id")
  @Mapping(target = "startTime", source = "movieShowEntity.startTime")
  @Mapping(target = "endTime", source = "movieShowEntity.endTime")
  @Mapping(target = "price", source = "movieShowEntity.price")
  @Mapping(target = "movieId", source = "movieShowEntity.movieEntity.id")
  @Mapping(target = "roomId", source = "movieShowEntity.roomEntity.id")
  MovieShow toDomain(MovieShowEntity movieShowEntity);

  @Mapping(target = "bookingEntities", ignore = true)
  @Mapping(target = "id", source = "movieShow.id")
  @Mapping(target = "startTime", source = "movieShow.startTime")
  @Mapping(target = "endTime", source = "movieShow.endTime")
  @Mapping(target = "price", source = "movieShow.price")
  @Mapping(target = "movieEntity", source = "movieEntity")
  @Mapping(target = "roomEntity", source = "roomEntity")
  MovieShowEntity toEntity(MovieShow movieShow, RoomEntity roomEntity, MovieEntity movieEntity);
}
