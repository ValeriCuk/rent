package org.example.rent.services.mappers;

import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.ServicesDTO;
import org.example.rent.entity.Photo;
import org.example.rent.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = PhotoMapper.class)
public abstract class ServicesMapper {

    @Autowired
    private PhotoMapper photoMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "photos", ignore = true)
    public abstract Services toEntity(ServicesDTO dto);

    public Services toEntityWithRelations(ServicesDTO dto) {
        Services entity = toEntity(dto);
        if (dto.getPhotos() == null) return entity;
        if (!dto.getPhotos().isEmpty()) {
            List<Photo> photos = dto.getPhotos().stream()
                    .map(photoMapper::toEntity)
                    .toList();
            entity.setPhotos(photos);
        }
        return entity;
    }

    @Mapping(target = "photos", ignore = true)
    public abstract ServicesDTO toDto(Services entity);

    public ServicesDTO toDTOWithRelations(Services entity) {
        ServicesDTO dto = toDto(entity);

        if (!entity.getPhotos().isEmpty()) {
            List<PhotoDTO> photos = entity.getPhotos().stream()
                    .map(photoMapper::toDto)
                    .toList();
            dto.setPhotos(photos);
        }
        return dto;
    }
}
