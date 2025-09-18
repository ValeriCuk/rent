package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.entity.Building;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.BuildingRepository;
import org.example.rent.services.mappers.BuildingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;
    private final Logger log = CustomLogger.getLog();

    public BuildingService(BuildingRepository buildingRepository, BuildingMapper buildingMapper) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
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

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        if (!buildingRepository.existsById(id))
            throw new NotFoundException("Building with id " + id + " not found");
        buildingRepository.deleteById(id);
        log.info("Delete building with id: " + id);
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
