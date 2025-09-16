package org.example.rent.services.mappers.property;

import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.propertydto.ApartmentDTO;
import org.example.rent.entity.Building;
import org.example.rent.entity.property.Apartment;
import org.example.rent.services.mappers.BuildingMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BuildingMapper.class, PropertyMapper.class})
public interface ApartmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "building", ignore = true)
    Apartment toEntity(ApartmentDTO dto);

    default Apartment toEntityWithRelations(ApartmentDTO dto){
        Apartment entity = (Apartment) propertyMapper().toEntityWithRelations(dto);

        entity.setFloor(dto.getFloor());
        entity.setBedrooms(dto.getBedrooms());

        if (dto.getBuilding() == null) return entity;
        Building building = buildingMapper().toEntity(dto.getBuilding());
        entity.setBuilding(building);
        return entity;
    }

    @Mapping(target = "building", ignore = true)
    ApartmentDTO toDto(Apartment entity);

    default ApartmentDTO toDTOWithRelations(Apartment entity){
        ApartmentDTO dto = (ApartmentDTO) propertyMapper().toDTOWithRelations(entity);

        dto.setFloor(dto.getFloor());
        dto.setBedrooms(dto.getBedrooms());

        if (entity.getBuilding() == null) return dto;
        BuildingDTO building = buildingMapper().toDto(entity.getBuilding());
        dto.setBuilding(building);
        return dto;
    }

    PropertyMapper propertyMapper();
    BuildingMapper buildingMapper();
}
