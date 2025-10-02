package org.example.rent.services.property;

import org.example.rent.dto.propertydto.HouseDTO;
import org.example.rent.entity.property.House;
import org.example.rent.repositories.interfaces.properties.HouseRepository;
import org.example.rent.services.PhotoService;
import org.example.rent.services.ViewingRequestService;
import org.example.rent.services.mappers.PhotoMapper;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.springframework.stereotype.Service;

@Service
public class HouseService extends PropertyService<House, HouseDTO> {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final HouseRepository houseRepository;
    private final PropertyMapper propertyMapper;

    public HouseService(HouseRepository houseRepository,
                        PropertyMapper propertyMapper,
                        PhotoService photoService,
                        PhotoMapper photoMapper,
                        ViewingRequestService viewingRequestService) {
        super(houseRepository, propertyMapper, photoService, photoMapper,  viewingRequestService);
        this.houseRepository = houseRepository;
        this.propertyMapper = propertyMapper;
        this.photoService = photoService;
        this.photoMapper = photoMapper;
    }
}
