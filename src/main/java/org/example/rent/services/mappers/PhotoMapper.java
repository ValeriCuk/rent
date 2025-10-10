package org.example.rent.services.mappers;

import org.example.rent.dto.PhotoDTO;
import org.example.rent.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class  PhotoMapper {

    @Mapping(target = "id", ignore = true)
    public abstract Photo toEntity(PhotoDTO dto);

    public Photo toEntityWithRelations(PhotoDTO dto){
        Photo entity = toEntity(dto);
        if (dto.getId() != null) entity.setId(dto.getId());
        return entity;
    }

    public abstract PhotoDTO toDto(Photo photo);
}
