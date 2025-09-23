package org.example.rent.services.mappers;

import org.example.rent.dto.LocationDTO;
import org.example.rent.other.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDTO toDto(Location location);
    Location toEntity(LocationDTO dto);
}
