package com.test.hex.cinehex.infrastructure.adapter.in.mapper;

import com.test.hex.cinehex.domain.model.Movie;
import com.test.hex.cinehex.domain.port.in.command.CreateMovieCommand;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.MovieRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.MovieResponse;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieRestMapper {
    CreateMovieCommand toCommand(MovieRequest request);

    MovieResponse toResponse(Movie movie);
}
