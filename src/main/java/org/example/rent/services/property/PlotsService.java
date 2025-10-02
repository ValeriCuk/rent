package org.example.rent.services.property;

import org.example.rent.dto.propertydto.PlotsDTO;
import org.example.rent.entity.property.Plots;
import org.example.rent.repositories.interfaces.properties.PlotsRepository;
import org.example.rent.services.PhotoService;
import org.example.rent.services.ViewingRequestService;
import org.example.rent.services.mappers.PhotoMapper;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.springframework.stereotype.Service;

@Service
public class PlotsService extends PropertyService<Plots, PlotsDTO> {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final PlotsRepository plotsRepository;
    private final PropertyMapper propertyMapper;


    public PlotsService(PlotsRepository plotsRepository,
                        PropertyMapper propertyMapper,
                        PhotoService photoService,
                        PhotoMapper photoMapper,
                        ViewingRequestService viewingRequestService) {
        super(plotsRepository, propertyMapper, photoService, photoMapper, viewingRequestService);
        this.plotsRepository = plotsRepository;
        this.propertyMapper = propertyMapper;
        this.photoService = photoService;
        this.photoMapper = photoMapper;
    }
}
