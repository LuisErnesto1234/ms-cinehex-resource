package com.test.hex.cinehex.infrastructure.adapter.in.mapper;

import com.test.hex.cinehex.domain.model.User;
import com.test.hex.cinehex.domain.port.in.command.CreateUserCommand;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.request.UserRequest;
import com.test.hex.cinehex.infrastructure.adapter.in.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserRestMapper {

    CreateUserCommand toCommand(UserRequest request);

    UserResponse toResponse(User user);

}
