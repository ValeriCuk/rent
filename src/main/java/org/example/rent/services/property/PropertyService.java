package org.example.rent.services.property;

import org.apache.logging.log4j.Logger;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.properties.PropertyRepository;
import org.example.rent.services.mappers.property.PropertyMapper;
import org.example.rent.entity.property.Property;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class PropertyService<T extends Property, D extends PropertyDTO> {

    protected final PropertyRepository<T> propertyRepository;
    protected final PropertyMapper propertyMapper;
    private final Logger log = CustomLogger.getLog();

    protected PropertyService(PropertyRepository<T> propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    //getById(Long id)
    public D getById(Long id) {
        T property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found through PropertyService(getById) with id: " + id));
        D dto = (D) propertyMapper.toDTOWithRelations(property);
        log.info("Get property through PropertyService with id: " + id);
        return dto;
    }

    //getAll()
    public List<D> getAll() {
        List<T> list = propertyRepository.findAll();
        List<D> dtoList = (List<D>) list.stream()
                .map(propertyMapper::toDTOWithRelations)
                .collect(Collectors.toList());
        log.info("Get all property through PropertyService with dtoList size: " + dtoList.size() + ", type: " + dtoList.get(0).getClass());
        return dtoList;
    }

    //save(DTO dto)
    @Transactional
    public void save(D dto) {
        T property = (T) propertyMapper.toEntityWithRelations(dto);
        propertyRepository.save(property);
        log.info("Save property through PropertyService with id: " + property.getId() + ", type: " + property.getClass());
    }

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        T property = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found through PropertyService(delete) with id: " + id));
        propertyRepository.delete(property);
        log.info("Delete property through PropertyService with id: " + id + ", type: " + property.getClass());
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
