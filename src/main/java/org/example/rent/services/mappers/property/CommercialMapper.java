package org.example.rent.services.mappers.property;

import org.example.rent.dto.propertydto.CommercialDTO;
import org.example.rent.entity.property.Commercial;
import org.example.rent.services.mappers.LocationMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {
        PropertyMapper.class,
        LocationMapper.class})
public abstract class CommercialMapper {

    @Autowired
    LocationMapper locationMapper;

    @Mapping(target = "id", ignore = true)
    @BeanMapping(builder = @Builder( disableBuilder = true ))
    public abstract Commercial toEntity(CommercialDTO dto);

    public Commercial toEntityWithRelations(CommercialDTO dto, PropertyMapper propertyMapper) {
        Commercial entity = (Commercial) propertyMapper.toEntity(dto);

        entity.setFloor(dto.getFloor());

        return entity;
    }

    public abstract CommercialDTO toDTO(Commercial entity);

    public CommercialDTO toDTOWithRelations(Commercial entity, PropertyMapper propertyMapper) {
        CommercialDTO dto = (CommercialDTO) propertyMapper.toDto(entity);
        dto.setFloor(entity.getFloor());
        return dto;
    }
}
