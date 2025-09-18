package org.example.rent.services.mappers;

import org.example.rent.dto.PhotoDTO;
import org.example.rent.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    @Mapping(target = "id", ignore = true)
    Photo toEntity(PhotoDTO dto);

    PhotoDTO toDto(Photo photo);
}
