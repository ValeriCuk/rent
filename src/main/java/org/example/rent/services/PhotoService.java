package org.example.rent.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.entity.Photo;
import org.example.rent.exceptions.FileSavingException;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.PhotoType;
import org.example.rent.repositories.interfaces.PhotoRepository;
import org.example.rent.services.mappers.PhotoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhotoService {

    private final Path rootLocation = Paths.get("uploads/photos");
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
    public PhotoDTO save(PhotoDTO photoDTO) {
        Photo photo = photoMapper.toEntity(photoDTO);
        Photo save = photoRepository.save(photo);
        log.info("Save photo with id: " + photo.getId());
        PhotoDTO savedPhotoDTO = photoMapper.toDto(save);
        savedPhotoDTO.setId(save.getId());
        return savedPhotoDTO;
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

    //savePhotoFile()
    @Transactional
    public PhotoDTO store(MultipartFile file, PhotoType type){
        try {
            String originalFilename = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(originalFilename);
            String fileName = UUID.randomUUID().toString() + "." + ext;

            Path destination = rootLocation.resolve(fileName);
            Files.createDirectories(destination.getParent());
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            PhotoDTO photo = new PhotoDTO();
            photo.setFileName(fileName);
            photo.setFormat(ext);
            photo.setSize(file.getSize());
            photo.setUrl("/uploads/photos/" + photo.getFileName());
            photo.setType(type);
            log.info("Save photo file with url: " + photo.getUrl());
            return photo;
        }catch (IOException ex){
            throw new FileSavingException("Error storing file: " + ex.getMessage());
        }
    }

    public void deletePhotoFile(String filePath) {
        try {
            Path path = Paths.get("." + filePath);
            if (Files.deleteIfExists(path)) {
                log.info("Delete photo file with path: " + filePath);
            } else {
                log.info("Deleted was failed");
            }
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося видалити файл: " + filePath, e);
        }
    }
}
