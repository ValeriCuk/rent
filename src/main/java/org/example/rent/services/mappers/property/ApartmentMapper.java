package org.example.rent.services.mappers.property;

import org.example.rent.dto.propertydto.ApartmentDTO;
import org.example.rent.entity.property.Apartment;
import org.example.rent.services.mappers.BuildingMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {BuildingMapper.class, PropertyMapper.class})
public abstract class ApartmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "building", ignore = true)
    public abstract Apartment toEntity(ApartmentDTO dto);

    public Apartment toEntityWithRelations(ApartmentDTO dto, PropertyMapper propertyMapper) {

        Apartment entity = (Apartment) propertyMapper.toEntity(dto);

        entity.setFloor(dto.getFloor());
        entity.setBedrooms(dto.getBedrooms());
        return entity;
    }

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "building", ignore = true)
    public abstract ApartmentDTO toDto(Apartment entity);

    public ApartmentDTO toDTOWithRelations(Apartment entity, PropertyMapper propertyMapper) {
        ApartmentDTO dto = (ApartmentDTO) propertyMapper.toDto(entity);

        dto.setFloor(dto.getFloor());
        dto.setBedrooms(dto.getBedrooms());

        return dto;
    }
}
