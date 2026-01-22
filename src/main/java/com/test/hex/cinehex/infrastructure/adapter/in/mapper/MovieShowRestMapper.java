package com.test.hex.cinehex.infrastructure.adapter.in.mapper;

import com.test.hex.cinehex.domain.model.details.MovieShowDetails;
import com.test.hex.cinehex.domain.port.in.command.CreateMovieShowCommand;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.MovieShowRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.MovieShowResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoomRestMapper.class, MovieRestMapper.class})
public interface MovieShowRestMapper {

    @Mapping(target = "movieId", source = "movieShowRequest.movieId")
    @Mapping(target = "roomId", source = "movieShowRequest.roomId")
    @Mapping(target = "startTime", source = "movieShowRequest.startTime")
    @Mapping(target = "price", source = "movieShowRequest.price", qualifiedByName = "stringToBigDecimal")
    CreateMovieShowCommand toCommand(MovieShowRequest movieShowRequest);

    @Mapping(target = "id", source = "movieShowDetails.id")
    @Mapping(target = "starTime", source = "movieShowDetails.startTime")
    @Mapping(target = "endTime", source = "movieShowDetails.endTime")
    @Mapping(target = "price", source = "movieShowDetails.price")
    @Mapping(target = "movieResponse", source = "movie")
    @Mapping(target = "roomResponse", source = "room")
    MovieShowResponse toResponse(MovieShowDetails movieShowDetails);

    @Named("stringToBigDecimal")
    default BigDecimal stringToBigDecimal(String price) {
        return price != null ? new BigDecimal(price) : null;
    }

}
