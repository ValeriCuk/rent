package org.example.rent.services.mappers;

import org.example.rent.dto.OrderDTO;
import org.example.rent.entity.User;
import org.example.rent.dto.UserDTO;
import org.example.rent.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDTO dto);

    UserDTO toDto(User user);
}
