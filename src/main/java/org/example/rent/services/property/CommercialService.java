package org.example.rent.services.property;

import org.example.rent.dto.propertydto.CommercialDTO;
import org.example.rent.entity.property.Commercial;
import org.example.rent.repositories.interfaces.properties.CommercialRepository;
import org.example.rent.repositories.interfaces.properties.PropertyRepository;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.springframework.stereotype.Service;

@Service
public class CommercialService extends PropertyService<Commercial, CommercialDTO> {

    private final CommercialRepository commercialRepository;
    private final PropertyMapper propertyMapper;

    public CommercialService(CommercialRepository commercialRepository, PropertyMapper propertyMapper) {
        super(commercialRepository, propertyMapper);
        this.commercialRepository = commercialRepository;
        this.propertyMapper = propertyMapper;
    }


}
