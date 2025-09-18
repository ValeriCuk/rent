package org.example.rent.services.mappers;

import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.ServicesDTO;
import org.example.rent.entity.Photo;
import org.example.rent.entity.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PhotoMapper.class)
public interface ServicesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "photosDTO", ignore = true)
    Services toEntity(ServicesDTO dto);

    default Services toEntityWithRelations(ServicesDTO dto) {
        Services entity = toEntity(dto);
        if (!dto.getPhotosDTO().isEmpty()) {
            List<Photo> photos = dto.getPhotosDTO().stream()
                    .map(photoMapper()::toEntity)
                    .toList();
            entity.setPhotos(photos);
        }
        return entity;
    }

    @Mapping(target = "photos", ignore = true)
    ServicesDTO toDto(Services entity);

    default ServicesDTO toEntityWithRelations(Services entity) {
        ServicesDTO dto = toDto(entity);

        if (!entity.getPhotos().isEmpty()) {
            List<PhotoDTO> photos = entity.getPhotos().stream()
                    .map(photoMapper()::toDto)
                    .toList();
            dto.setPhotosDTO(photos);
        }
        return dto;
    }

    PhotoMapper photoMapper();
}
