package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.entity.Building;
import org.example.rent.entity.Photo;
import org.example.rent.entity.Services;
import org.example.rent.exceptions.InvalidStatusException;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.BuildingStatus;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.PhotoType;
import org.example.rent.other.ServicesStatus;
import org.example.rent.repositories.interfaces.BuildingRepository;
import org.example.rent.repositories.interfaces.ViewingRequestRepository;
import org.example.rent.repositories.interfaces.properties.PropertyRepository;
import org.example.rent.services.mappers.BuildingMapper;
import org.example.rent.services.mappers.PhotoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BuildingService {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;
    private final PropertyRepository propertyRepository;
    private final Logger log = CustomLogger.getLog();
    private final ViewingRequestRepository viewingRequestRepository;

    public BuildingService(
            BuildingRepository buildingRepository,
            BuildingMapper buildingMapper,
            PhotoService photoService,
            PhotoMapper photoMapper,
            PropertyRepository propertyRepository,
            ViewingRequestRepository viewingRequestRepository) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
        this.photoService = photoService;
        this.photoMapper = photoMapper;
        this.propertyRepository = propertyRepository;
        this.viewingRequestRepository = viewingRequestRepository;
    }

    //getById(Long id)
    public BuildingDTO getById(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building with id " + id + " not found"));
        log.info("Get building with id: " + id);
        return buildingMapper.toDtoWithRelations(building);
    }

    public String getURLPhoto(BuildingDTO dto, PhotoType type){
        return dto.getPhotos().stream()
                .filter(photo -> photo.getType() == type)
                .map(Photo::getUrl)
                .findFirst()
                .orElse(null);
    }

    public List<String> getURLsPhoto(BuildingDTO dto, PhotoType type){
        return dto.getPhotos().stream()
                .filter(photo -> photo.getType() == type)
                .map(Photo::getUrl)
                .collect(Collectors.toList());
    }

    //getAll()
    public List<BuildingDTO> getAll() {
        List<Building> buildings = buildingRepository.findAll();
        log.info("Get all buildings");
        return buildings.stream().map(buildingMapper::toDtoWithRelations).collect(Collectors.toList());
    }

    //getFilteredPages
    public Page<BuildingDTO> getFilteredPage(String title, AddressDTO addressDTO, BuildingStatus status, int page, int size) {
        Specification<Building> spec = (root, query, cb) -> cb.conjunction();

        if (title != null && !title.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<Building> entityPage = buildingRepository.findAll(spec, pageable);

        log.info("Get all buildings");
        return entityPage.map(buildingMapper::toDtoWithRelations);
    }

    //save(DTO dto)
    @Transactional
    public void save(BuildingDTO dto) {
        Building building = buildingMapper.toEntityWithRelations(dto);
        buildingRepository.save(building);
        log.info("Save building with id: " + building.getId());
    }

    //savePhoto
    @Transactional
    public PhotoDTO storePhotoBuilding(Long id, MultipartFile file) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Building not found"));

        PhotoDTO photoDTO = photoService.store(file, PhotoType.BANNER);
        Photo photoFoSaving = photoMapper.toEntity(photoDTO);
        photoFoSaving.setId(photoDTO.getId());
        building.getPhotos().add(photoFoSaving);

        buildingRepository.save(building); // зберігає зв’язок
        return photoDTO;
    }

    //deleteAll()
    @Transactional
    public void deleteAll(){
        viewingRequestRepository.deleteAllByPropertyBuildingIsNotNull();
        propertyRepository.deleteByBuildingIsNotNull();
        buildingRepository.deleteAll();
        log.info("Delete all buildings");
    }

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        if (!buildingRepository.existsById(id))
            throw new NotFoundException("Building with id " + id + " not found");
        viewingRequestRepository.deleteByPropertyBuildingId(id);
        propertyRepository.deleteByBuildingId(id);
        buildingRepository.deleteById(id);
        log.info("Delete building with id: " + id);
    }

    //updateStatus(Long id, BuildingStatus status)
    @Transactional
    public void updateStatusBuilding(Long id){
        Building building = buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building with id " + id + " not found"));
        toggleStatus(building);
        buildingRepository.save(building);
        log.info("Update status of building with id: " + id);
    }

    private void toggleStatus(Building building) {
        if (building.getStatus() == BuildingStatus.ACTIVE) {
            building.setStatus(BuildingStatus.INACTIVE);
        } else {
            building.setStatus(BuildingStatus.ACTIVE);
        }
    }

    //update(Long id, DTO dto)
    @Transactional
    public void update(Long id, BuildingDTO dto) {
        if (!buildingRepository.existsById(id))
            throw new NotFoundException("Building with id " + id + " not found");
        Building building = buildingMapper.toEntityWithRelations(dto);
        building.setId(id);
        buildingRepository.save(building);
        log.info("Update building with id: " + id);
    }

}
