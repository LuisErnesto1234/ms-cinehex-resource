package com.test.hex.cinehex.infrastructure.adapter.out.persistence.mapper;

import com.test.hex.cinehex.domain.model.User;
import com.test.hex.cinehex.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {

    User toDomain(UserEntity userEntity);

    UserEntity toEntity(User user);
}
