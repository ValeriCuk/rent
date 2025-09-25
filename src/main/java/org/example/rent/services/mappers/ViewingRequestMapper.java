package org.example.rent.services.mappers;

import org.example.rent.dto.ViewingRequestDTO;
import org.example.rent.dto.UserDTO;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.example.rent.entity.ViewingRequest;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.example.rent.entity.User;
import org.example.rent.entity.property.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring" , uses = {
        PropertyMapper.class,
        UserMapper.class})
public abstract class ViewingRequestMapper {

    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private UserMapper userMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "property", ignore = true)
    public abstract ViewingRequest toEntity(ViewingRequestDTO dto);

    public ViewingRequest toEntityWithRelations(ViewingRequestDTO dto) {
        ViewingRequest entity = toEntity(dto);
        User user = userMapper.toEntity(dto.getUser());
        entity.setUser(user);
        if (dto.getProperty() != null) {
            Property property = propertyMapper.toEntityWithRelations(dto.getProperty());
            entity.setProperty(property);
        }
        return entity;
    }

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "property", ignore = true)
    public abstract ViewingRequestDTO toDto(ViewingRequest viewingRequest);

    public ViewingRequestDTO toDtoWithRelations(ViewingRequest entity){
        ViewingRequestDTO dto = toDto(entity);
        UserDTO user = userMapper.toDto(entity.getUser());
        dto.setUser(user);
        if (entity.getProperty() != null) {
            PropertyDTO property = propertyMapper.toDTOWithRelations(entity.getProperty());
            dto.setProperty(property);
        }
        return dto;
    }
}
