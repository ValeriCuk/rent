package org.example.rent.services.property;

import org.example.rent.dto.propertydto.PlotsDTO;
import org.example.rent.entity.property.Plots;
import org.example.rent.repositories.interfaces.properties.PlotsRepository;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.springframework.stereotype.Service;

@Service
public class PlotsService extends PropertyService<Plots, PlotsDTO> {

    private final PlotsRepository plotsRepository;
    private final PropertyMapper propertyMapper;

    public PlotsService(PlotsRepository plotsRepository, PropertyMapper propertyMapper) {
        super(plotsRepository, propertyMapper);
        this.plotsRepository = plotsRepository;
        this.propertyMapper = propertyMapper;
    }
}
