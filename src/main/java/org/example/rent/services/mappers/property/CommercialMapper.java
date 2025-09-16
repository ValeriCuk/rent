package org.example.rent.services.mappers.property;

import org.example.rent.dto.propertydto.CommercialDTO;
import org.example.rent.entity.property.Commercial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommercialMapper {

    @Mapping(target = "id", ignore = true)
    Commercial toEntity(CommercialDTO dto);

    CommercialDTO toDTO(Commercial entity);
}
