package org.example.rent.services.mappers;

import org.example.rent.dto.ServicesDTO;
import org.example.rent.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServicesMapper {

    @Mapping(target = "id", ignore = true)
    Services toEntity(ServicesDTO dto);

    ServicesDTO toDto(Services entity);
}
