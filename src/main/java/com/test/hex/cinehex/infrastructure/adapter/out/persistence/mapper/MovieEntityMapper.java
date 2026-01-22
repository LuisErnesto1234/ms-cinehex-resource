package com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper;

import com.test.hex.cinehex.domain.model.Movie;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieEntityMapper {
    Movie toDomain(MovieEntity entity);

    @Mapping(target = "movieShowEntities", ignore = true)
    MovieEntity toEntity(Movie domain);
}
