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
    @Mapping(target = "propertyDTO", ignore = true)
    @Mapping(target = "buildingDTO", ignore = true)
    @Mapping(target = "servicesDTO", ignore = true)
    Photo toEntity(PhotoDTO dto);

    default Photo toEntityWithRelations(PhotoDTO dto) {
        Photo entity = toEntity(dto);
        if (dto.getPropertyDTO() != null) {
            Property property = propertyMapper().toEntityWithRelations(dto.getPropertyDTO());
            entity.setProperty(property);
        }
        if (dto.getBuildingDTO() != null) {
            Building building = buildingMapper().toEntityWithRelations(dto.getBuildingDTO());
            entity.setBuilding(building);
        }
        if (dto.getServicesDTO() != null) {
            Services services = servicesMapper().toEntityWithRelations(dto.getServicesDTO());
        }
        return entity;
    }

    @Mapping(target = "property", ignore = true)
    @Mapping(target = "building", ignore = true)
    @Mapping(target = "services", ignore = true)
    PhotoDTO toDto(Photo photo);
    default PhotoDTO toDtoWithRelations(Photo entity) {
        PhotoDTO dto = toDto(entity);
        if (entity.getProperty() != null) {
            PropertyDTO property = propertyMapper().toDTOWithRelations(entity.getProperty());
            dto.setPropertyDTO(property);
        }
        if (entity.getBuilding() != null) {
            BuildingDTO building = buildingMapper().toDtoWithRelations(entity.getBuilding());
            dto.setBuildingDTO(building);
        }
        if (entity.getServices() != null) {
            ServicesDTO services = servicesMapper().toEntityWithRelations(entity.getServices());
            dto.setServicesDTO(services);
        }
        return dto;
    }

    PropertyMapper propertyMapper();
    BuildingMapper buildingMapper();
    ServicesMapper servicesMapper();
}
