package org.example.rent.services.mappers.property;

import org.example.rent.dto.propertydto.PlotsDTO;
import org.example.rent.entity.property.Plots;
import org.example.rent.services.mappers.LocationMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {
        PropertyMapper.class,
        LocationMapper.class})
public abstract class PlotsMapper {
    @Autowired
    LocationMapper locationMapper;

    @Mapping(target = "id", ignore = true)
    @BeanMapping(builder = @Builder( disableBuilder = true ))
    public abstract Plots toEntity(PlotsDTO dto);

    public Plots toEntityWithRelations(PlotsDTO dto, PropertyMapper propertyMapper) {
        return (Plots) propertyMapper.toEntity(dto);
    }

    public abstract PlotsDTO toDto(Plots entity);

    public  PlotsDTO toDtoWithRelations(Plots entity, PropertyMapper propertyMapper) {
        return (PlotsDTO) propertyMapper.toDto(entity);
    }
}
