package org.example.rent.services.mappers;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.ServicesDTO;
import org.example.rent.entity.Photo;
import org.example.rent.entity.Services;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.PhotoType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PhotoMapper.class)
public abstract class ServicesMapper {

    @Autowired
    private PhotoMapper photoMapper;
    private Logger log = CustomLogger.getLog();

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "photos", ignore = true)
    public abstract Services toEntity(ServicesDTO dto);

    public Services toEntityWithRelations(ServicesDTO dto) {
        Services entity = toEntity(dto);
        log.info("toEntityWithRelations -> dto: " + dto.getId());
        if (!dto.getPhotos().isEmpty()) {
            log.info("photo mapped in services: " + dto.getPhotos());
            List<Photo> photos = dto.getPhotos().stream()
                    .map(photoMapper::toEntityWithRelations)
                    .collect(Collectors.toList());
            entity.setPhotos(photos);
        }
        return entity;
    }

    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "banner", ignore = true)
    @Mapping(target = "bannerPhotoDTO", ignore = true)
    @Mapping(target = "previewPhotoDTO", ignore = true)
    public abstract ServicesDTO toDto(Services entity);

    public ServicesDTO toDTOWithRelations(Services entity) {
        ServicesDTO dto = toDto(entity);
        log.info("ServicesMapper: toDTOWithRelations, photo size is " + entity.getPhotos().size());
        if (!entity.getPhotos().isEmpty()) {
            List<PhotoDTO> photos = entity.getPhotos().stream()
                    .map(photoMapper::toDto)
                    .collect(Collectors.toList());
            dto.setPhotos(photos);
            dto.setBannerPhotoDTO(photos.stream().filter(p -> p.getType() == PhotoType.BANNER).findFirst().orElse(null));
            dto.setPreviewPhotoDTO(photos.stream().filter(p -> p.getType() == PhotoType.PREVIEW).findFirst().orElse(null));
        }
        return dto;
    }
}
