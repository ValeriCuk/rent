package org.example.rent.services.property;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.entity.Photo;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.PhotoType;
import org.example.rent.repositories.interfaces.properties.PropertyRepository;
import org.example.rent.services.PhotoService;
import org.example.rent.services.ViewingRequestService;
import org.example.rent.services.mappers.PhotoMapper;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.example.rent.entity.property.Property;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class PropertyService<T extends Property, D extends PropertyDTO> {

    protected final PhotoService photoService;
    protected final PhotoMapper photoMapper;
    protected final PropertyRepository<T> propertyRepository;
    protected final PropertyMapper propertyMapper;
    private final Logger log = CustomLogger.getLog();
    private final ViewingRequestService viewingRequestService;

    protected PropertyService(
            PropertyRepository<T> propertyRepository,
            PropertyMapper propertyMapper,
            PhotoService photoService,
            PhotoMapper photoMapper,
            ViewingRequestService viewingRequestService) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
        this.photoService = photoService;
        this.photoMapper = photoMapper;
        this.viewingRequestService = viewingRequestService;
    }

    //getById(Long id)
    public D getById(Long id) {
        T property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found through PropertyService(getById) with id: " + id));
        D dto = (D) propertyMapper.toDTOWithRelations(property);
        log.info("Get property through PropertyService with id: " + id);
        return dto;
    }

    //getAllAsync
    @Async
    public CompletableFuture<List<D>> getAllAsync() {
        List<T> list = propertyRepository.findAll();

        if (list.isEmpty()) {
            log.info("The property list is empty in method getAllAsync, class - PropertyService");
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        List<D> dtoList = (List<D>) list.stream()
                .map(propertyMapper::toDTOWithRelations)
                .collect(Collectors.toList());
        log.info("The property list through PropertyService in method getAllAsync, list size: " + dtoList.size() + ", type: " + dtoList.get(0).getClass());
        return CompletableFuture.completedFuture(dtoList);
    }

    //save(DTO dto)
    @Transactional
    public void save(D dto) {
        T property = (T) propertyMapper.toEntityWithRelations(dto);
        propertyRepository.save(property);
        log.info("Save property through PropertyService with id: " + property.getId() + ", type: " + property.getClass());
    }

    //savePhotoProperty(Long id, MultipartFile file)
    @Transactional
    public PhotoDTO storePhotoProperty(Long id, MultipartFile file) {
        T property = propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property not found"));

        PhotoDTO photoDTO = photoService.store(file, PhotoType.BANNER);
        Photo photoFoSaving = photoMapper.toEntity(photoDTO);
        photoFoSaving.setId(photoDTO.getId());
        property.getPhotos().add(photoFoSaving);

        propertyRepository.save(property);
        return photoDTO;
    }

    //deleteAll()
    @Transactional
    public void deleteAll() {
        viewingRequestService.deleteAll();
        propertyRepository.deleteAll();
        log.info("Delete all property through PropertyService with type: " + propertyRepository.getClass().getName());
    }

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        T property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found through PropertyService(delete) with id: " + id));
        propertyRepository.delete(property);
        log.info("Delete property through PropertyService with id: " + id + ", type: " + property.getClass());
    }

    //deleteWithBuilding(Long buildingId)
    public void deleteWithBuilding(Long buildingId) {
        propertyRepository.deleteByBuildingId(buildingId);
        log.info("Delete property through PropertyService with building id: " + buildingId);
    }

    //update(Long id, DTO dto)
    @Transactional
    public void update(Long id, D dto) {
        if (!propertyRepository.existsById(id))
            throw new NotFoundException("Not found through PropertyService(update) with id: " + id);
        T property = (T) propertyMapper.toEntityWithRelations(dto);
        property.setId(id);
        propertyRepository.save(property);
        log.info("Updated property through PropertyService with id: " + id);
    }
}
