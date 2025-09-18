package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.entity.Photo;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.PhotoRepository;
import org.example.rent.services.mappers.PhotoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;
    private final Logger log = CustomLogger.getLog();

    public PhotoService(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    //getById(Long id)
    public PhotoDTO getById(Long id) {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new NotFoundException("Photo with id: " + id + " not found"));
        log.info("Get photo with id: " + id);
        return photoMapper.toDto(photo);
    }

    //getAll()
    public List<PhotoDTO> getAll() {
        List<Photo> photos = photoRepository.findAll();
        log.info("Get all photos");
        return photos.stream().map(photoMapper::toDto).collect(Collectors.toList());
    }

    //save(DTO dto)
    @Transactional
    public void save(PhotoDTO photoDTO) {
        Photo photo = photoMapper.toEntity(photoDTO);
        photoRepository.save(photo);
        log.info("Save photo with id: " + photo.getId());
    }

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        if (!photoRepository.existsById(id))
            throw new NotFoundException("Photo with id: " + id + " not found");
        photoRepository.deleteById(id);
        log.info("Delete photo with id: " + id);
    }

    //update(Long id, DTO dto)
    @Transactional
    public void update(Long id, PhotoDTO photoDTO) {
        if (!photoRepository.existsById(id))
            throw new NotFoundException("Photo with id: " + id + " not found");
        Photo photo = photoMapper.toEntity(photoDTO);
        photo.setId(id);
        photoRepository.save(photo);
        log.info("Update photo with id: " + id);
    }
}
