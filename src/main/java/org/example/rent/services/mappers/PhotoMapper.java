package org.example.rent.services.mappers;

import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.ServicesDTO;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.example.rent.entity.Building;
import org.example.rent.entity.Photo;
import org.example.rent.entity.Services;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.example.rent.entity.property.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        PropertyMapper.class,
        BuildingMapper.class,
        ServicesMapper.class})
public interface PhotoMapper {

    @Mapping(target = "id", ignore = true)
    Photo toEntity(PhotoDTO dto);


    PhotoDTO toDto(Photo photo);

    PropertyMapper propertyMapper();
}
