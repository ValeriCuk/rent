package org.example.rent.other;

import org.example.rent.dto.propertydto.*;
import org.example.rent.entity.property.*;
import org.example.rent.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PropertyFactory {

    public Property create(PropertyDTO dto) {
        if (dto instanceof ApartmentDTO) return new Apartment();
        if (dto instanceof CommercialDTO) return new Commercial();
        if (dto instanceof HouseDTO) return new House();
        if (dto instanceof PlotsDTO) return new Plots();
        throw new NotFoundException("Невідомий тип DTO: " + dto.getClass());
    }

    public PropertyDTO createDTO(Property entity) {
        if (entity instanceof Apartment) return new ApartmentDTO();
        if (entity instanceof Commercial) return new CommercialDTO();
        if (entity instanceof House) return new HouseDTO();
        if (entity instanceof Plots) return new PlotsDTO();
        throw new NotFoundException("Невідомий тип Entity: " + entity.getClass());
    }
}