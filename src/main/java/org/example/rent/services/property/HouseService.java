package org.example.rent.services.property;

import org.example.rent.dto.propertydto.HouseDTO;
import org.example.rent.entity.property.House;
import org.example.rent.repositories.interfaces.properties.HouseRepository;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.springframework.stereotype.Service;

@Service
public class HouseService extends PropertyService<House, HouseDTO> {

    private final HouseRepository houseRepository;
    private final PropertyMapper propertyMapper;

    public HouseService(HouseRepository houseRepository, PropertyMapper propertyMapper) {
        super(houseRepository, propertyMapper);
        this.houseRepository = houseRepository;
        this.propertyMapper = propertyMapper;
    }
}
