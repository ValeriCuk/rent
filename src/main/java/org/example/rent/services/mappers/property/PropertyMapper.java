package org.example.rent.services.mappers.property;

import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.OrderDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.entity.Address;
import org.example.rent.entity.Order;
import org.example.rent.entity.Photo;
import org.example.rent.services.mappers.AddressMapper;
import org.example.rent.services.mappers.OrderMapper;
import org.example.rent.services.mappers.PhotoMapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.example.rent.entity.property.Property;
import org.example.rent.dto.propertydto.PropertyDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, PhotoMapper.class, OrderMapper.class})
public interface PropertyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "photos", ignore = true)
    Property toEntity(PropertyDTO dto);

    default Property toEntityWithRelations(PropertyDTO dto) {
        Property entity = toEntity(dto);

        List<Order> orders = dto.getOrders().stream()
                .map(orderMapper()::toEntity)
                .toList();
        entity.setOrders(orders);

        Address address = addressMapper().toEntity(dto.getAddress());
        entity.setAddress(address);

        List<Photo> photos = dto.getPhotos().stream()
                .map(photoMapper()::toEntity)
                .toList();
        entity.setPhotos(photos);

        return entity;
    }

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "photos", ignore = true)
    PropertyDTO toDto(Property entity);

    default PropertyDTO toDTOWithRelations(Property entity) {
        PropertyDTO dto = toDto(entity);
        List<OrderDTO> orders = entity.getOrders().stream()
                .map(orderMapper()::toDto)
                .toList();
        dto.setOrders(orders);

        AddressDTO address = addressMapper().toDto(entity.getAddress());
        dto.setAddress(address);

        List<PhotoDTO> photos = entity.getPhotos().stream()
                .map(photoMapper()::toDto)
                .toList();
        dto.setPhotos(photos);

        return dto;
    }

    AddressMapper addressMapper();
    OrderMapper orderMapper();
    PhotoMapper photoMapper();
}
