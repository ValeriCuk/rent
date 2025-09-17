package org.example.rent.services.mappers.property;

import org.example.rent.dto.propertydto.ApartmentDTO;
import org.example.rent.entity.property.Apartment;
import org.example.rent.services.mappers.BuildingMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BuildingMapper.class, PropertyMapper.class})
public interface ApartmentMapper {

    @Mapping(target = "id", ignore = true)
    Apartment toEntity(ApartmentDTO dto);

    default Apartment toEntityWithRelations(ApartmentDTO dto){
        Apartment entity = (Apartment) propertyMapper().toEntity(dto);

        entity.setFloor(dto.getFloor());
        entity.setBedrooms(dto.getBedrooms());
        return entity;
    }

    ApartmentDTO toDto(Apartment entity);

    default ApartmentDTO toDTOWithRelations(Apartment entity){
        ApartmentDTO dto = (ApartmentDTO) propertyMapper().toDto(entity);

        dto.setFloor(dto.getFloor());
        dto.setBedrooms(dto.getBedrooms());

        return dto;
    }

    PropertyMapper propertyMapper();
}
