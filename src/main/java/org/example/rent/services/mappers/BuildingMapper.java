package org.example.rent.services.mappers;

import org.example.rent.dto.BuildingDTO;
import org.example.rent.entity.Building;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BuildingMapper {

    @Mapping(target = "id", ignore = true)
    Building toEntity(BuildingDTO dto);

    BuildingDTO toDto(Building entity);
}
