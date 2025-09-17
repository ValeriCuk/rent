package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.properties.PropertyRepository;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.example.rent.entity.property.Property;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class PropertyService<T extends Property, D extends PropertyDTO> {

    protected final PropertyRepository propertyRepository;
    protected final PropertyMapper propertyMapper;
    private final Logger log = CustomLogger.getLog();

    protected PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    //getById(Long id)
    public D getById(Long id) {
        D dto = null;
        try {
            T property = (T) propertyRepository.findById(id).orElseThrow(NoSuchElementException::new);
            dto = (D) propertyMapper.toDTOWithRelations(property);
            log.info("Get property through PropertyService with id: " + id);
        }catch (NoSuchElementException e) {
            log.error("Not found through PropertyService with id: " + id, e);
        }
        return dto;
    }

    //getAll()
    public List<D> getAll() {
        List<T> list = (List<T>) propertyRepository.findAll();
        List<D> dtoList = (List<D>) list.stream()
                .map(propertyMapper::toDTOWithRelations)
                .collect(Collectors.toList());
        log.info("Get all property through PropertyService with dtoList size: " + dtoList.size());
        return dtoList;
    }
    //save(DTO dto)
    @Transactional
    public void save(D dto) {
        T property = (T) propertyMapper.toEntityWithRelations(dto);
        propertyRepository.save(property);
        log.info("Save property through PropertyService with id: " + property.getId());
    }
    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        try {
            T property = (T) propertyRepository.findById(id).orElseThrow(NoSuchElementException::new);
            propertyRepository.delete(property);
            log.info("Delete property through PropertyService with id: " + id);
        }catch (NoSuchElementException e) {
            log.error("Not found through PropertyService with id: " + id, e);
        }
    }

    //update(DTO dto)
    @Transactional
    public void update(Long id, D dto) {
        if (propertyRepository.findById(id).isPresent()) {
            T property = (T) propertyMapper.toEntityWithRelations(dto);
            property.setId(id);
            propertyRepository.save(property);
            log.info("Updated property through PropertyService with id: " + id);
        }else {
            log.error("Not found through PropertyService with id: " + dto.getId());
        }
    }
}
