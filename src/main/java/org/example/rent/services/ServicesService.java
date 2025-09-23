package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.ServicesDTO;
import org.example.rent.entity.Photo;
import org.example.rent.entity.Services;
import org.example.rent.exceptions.InvalidStatusException;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.ServicesStatus;
import org.example.rent.other.ViewingRequestStatus;
import org.example.rent.repositories.interfaces.ServicesRepository;
import org.example.rent.services.mappers.PhotoMapper;
import org.example.rent.services.mappers.ServicesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicesService {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final ServicesRepository servicesRepository;
    private final ServicesMapper servicesMapper;
    private final Logger log = CustomLogger.getLog();

    public ServicesService(ServicesRepository servicesRepository, ServicesMapper servicesMapper, PhotoService photoService, PhotoMapper photoMapper) {
        this.servicesRepository = servicesRepository;
        this.servicesMapper = servicesMapper;
        this.photoService = photoService;
        this.photoMapper = photoMapper;
    }

    //getById(Long id)
    public ServicesDTO findById(Long id) {
        Services services = servicesRepository.findById(id).orElseThrow(() -> new NotFoundException("Services with id: " + id + " not found"));
        log.info("Services found with id: " + id);
        return servicesMapper.toDTOWithRelations(services);
    }

    //getAll()
    public List<ServicesDTO> findAll() {
        List<Services> services = servicesRepository.findAll();
        log.info("Services found with all: " + services.size());
        return services.stream().map(servicesMapper::toDTOWithRelations).collect(Collectors.toList());
    }

    //save(DTO dto)
    @Transactional
    public void save(ServicesDTO dto) {
        Services entity = servicesMapper.toEntityWithRelations(dto);
        servicesRepository.save(entity);
        log.info("Services saved with id: " + entity.getId());
    }

    //savePhoto
    @Transactional
    public PhotoDTO savePhotoServices(Long id, MultipartFile file) {
        Services services = servicesRepository.findById(id).orElseThrow(() -> new NotFoundException("Services with id: " + id + " not found"));

        PhotoDTO photoDTO = photoService.store(file);
        Photo photoFoSaving = photoMapper.toEntity(photoDTO);
        photoFoSaving.setId(photoDTO.getId());
        services.getPhotos().add(photoFoSaving);

        servicesRepository.save(services);
        return photoDTO;
    }

    //deleteAll()
    @Transactional
    public void deleteAll(){
        servicesRepository.deleteAll();
        log.info("Services deleted all");
    }

    //delete(Long id)
    @Transactional
    public void deleteById(Long id) {
        if (!servicesRepository.existsById(id))
            throw new NotFoundException("Services with id: " + id + " not found");
        servicesRepository.deleteById(id);
        log.info("Services deleted with id: " + id);
    }

    //updateStatus(Long id, String status)
    @Transactional
    public void updateStatusServices(Long id, String status) {
        ServicesStatus newStatus = parseStatus(status);
        Services services = servicesRepository.findById(id).orElseThrow(() -> new NotFoundException("Services with id: " + id + " not found"));
        services.setStatus(newStatus);
        servicesRepository.save(services);
        log.info("Services status changed with id: " + id);
    }


    private ServicesStatus parseStatus(String status){
        try {
            return ServicesStatus.valueOf(status.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new InvalidStatusException("Invalid services status: " + status);
        }
    }
    //update(Long id, DTO dto)
    @Transactional
    public void update(Long id, ServicesDTO dto) {
        if (!servicesRepository.existsById(id))
            throw new NotFoundException("Services with id: " + id + " not found");
        Services services = servicesMapper.toEntityWithRelations(dto);
        services.setId(id);
        servicesRepository.save(services);
        log.info("Services saved with id: " + id);
    }
}
