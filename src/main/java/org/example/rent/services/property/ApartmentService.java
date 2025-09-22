package org.example.rent.services.property;

import org.example.rent.dto.propertydto.ApartmentDTO;
import org.example.rent.entity.property.Apartment;
import org.example.rent.repositories.interfaces.properties.ApartmentRepository;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.springframework.stereotype.Service;

@Service
public class ApartmentService extends PropertyService<Apartment, ApartmentDTO> {

    private final ApartmentRepository apartmentRepository;
    private final PropertyMapper propertyMapper;

    public ApartmentService(ApartmentRepository apartmentRepository, PropertyMapper propertyMapper) {
        super(apartmentRepository, propertyMapper);
        this.apartmentRepository = apartmentRepository;
        this.propertyMapper = propertyMapper;
    }


}
