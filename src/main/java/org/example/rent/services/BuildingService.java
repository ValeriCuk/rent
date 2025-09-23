package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.entity.Building;
import org.example.rent.entity.Photo;
import org.example.rent.exceptions.InvalidStatusException;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.BuildingStatus;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.BuildingRepository;
import org.example.rent.services.mappers.BuildingMapper;
import org.example.rent.services.mappers.PhotoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingService {

    private final PhotoService photoService;
    private final PhotoMapper photoMapper;
    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;
    private final Logger log = CustomLogger.getLog();

    public BuildingService(
            BuildingRepository buildingRepository,
            BuildingMapper buildingMapper,
            PhotoService photoService,
            PhotoMapper photoMapper) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
        this.photoService = photoService;
        this.photoMapper = photoMapper;
    }

    //getById(Long id)
    public BuildingDTO getById(Long id) {
        Building building = buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building with id " + id + " not found"));
        log.info("Get building with id: " + id);
        return buildingMapper.toDtoWithRelations(building);
    }

    //getAll()
    public List<BuildingDTO> getAll() {
        List<Building> buildings = buildingRepository.findAll();
        log.info("Get all buildings");
        return buildings.stream().map(buildingMapper::toDtoWithRelations).collect(Collectors.toList());
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

        PhotoDTO photoDTO = photoService.store(file);
        Photo photoFoSaving = photoMapper.toEntity(photoDTO);
        photoFoSaving.setId(photoDTO.getId());
        building.getPhotos().add(photoFoSaving);

        buildingRepository.save(building); // зберігає зв’язок
        return photoDTO;
    }

    //deleteAll()
    @Transactional
    public void deleteAll(){
        buildingRepository.deleteAll();
        log.info("Delete all buildings");
    }

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        if (!buildingRepository.existsById(id))
            throw new NotFoundException("Building with id " + id + " not found");
        buildingRepository.deleteById(id);
        log.info("Delete building with id: " + id);
    }

    //updateStatus(Long id, BuildingStatus status)
    @Transactional
    public void updateStatus(Long id, String status){
        BuildingStatus newStatus = parseStatus(status);
        Building building = buildingRepository.findById(id).orElseThrow(() -> new NotFoundException("Building with id " + id + " not found"));
        building.setStatus(newStatus);
        buildingRepository.save(building);
        log.info("Update status of building with id: " + id);
    }

    private BuildingStatus parseStatus(String status){
        try {
            return BuildingStatus.valueOf(status.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new InvalidStatusException("Invalid building status: " + status);
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
