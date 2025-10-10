package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.ServicesDTO;
import org.example.rent.dto.ViewingRequestDTO;
import org.example.rent.entity.Photo;
import org.example.rent.entity.Services;
import org.example.rent.entity.ViewingRequest;
import org.example.rent.exceptions.InvalidStatusException;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.PhotoType;
import org.example.rent.other.ServicesStatus;
import org.example.rent.other.ViewingRequestStatus;
import org.example.rent.repositories.interfaces.ServicesRepository;
import org.example.rent.services.mappers.PhotoMapper;
import org.example.rent.services.mappers.ServicesMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    //getFilteredPages
    public Page<ServicesDTO> getFilteredPage(String title, ServicesStatus status, int page, int size) {
        Specification<Services> spec = (root, query, cb) -> cb.conjunction();

        if (title != null && !title.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Services> entityPage = servicesRepository.findAll(spec, pageable);
        log.info("Found " + entityPage.getTotalPages() + " pages");
        return entityPage.map(servicesMapper::toDTOWithRelations);
    }

    //save(DTO dto)
    @Transactional
    public void save(ServicesDTO dto) {
        Services entity = servicesMapper.toEntityWithRelations(dto);
        servicesRepository.save(entity);
        log.info("Services saved with id: " + entity.getId());
    }

    //create(Services DTO dto)
    @Transactional
    public void create(ServicesDTO dto) {

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
    public void updateStatusServices(Long id) {
        Services services = servicesRepository.findById(id).orElseThrow(() -> new NotFoundException("Services with id: " + id + " not found"));
        toggleStatus(services);
        servicesRepository.save(services);
        log.info("Services status changed with id: " + id);
    }

    private void toggleStatus(Services ser) {
        if (ser.getStatus() == ServicesStatus.SHOW) {
            ser.setStatus(ServicesStatus.HIDE);
        } else {
            ser.setStatus(ServicesStatus.SHOW);
        }
    }

    @Transactional
    public void updateWithPhoto(ServicesDTO dto, Long bannerId, Long previewId) {

        if (!servicesRepository.existsById(dto.getId()))
            throw new NotFoundException("Services with id: " + dto.getId() + " not found");

        log.info("Updating photo with banner file: " + dto.getBanner());
        PhotoDTO bannerDTO = updatePhoto(dto.getBanner(), bannerId, PhotoType.BANNER);

        log.info("Updating photo with preview file: " + dto.getPreview());
        PhotoDTO previewDTO = updatePhoto(dto.getPreview(), previewId, PhotoType.PREVIEW);

        log.info("Banner DTO " + bannerDTO);
        log.info("Preview DTO " + previewDTO);

        if (bannerDTO != null)
            dto.getPhotos().add(bannerDTO);

        if (previewDTO != null)
            dto.getPhotos().add(previewDTO);

        Services entity = servicesMapper.toEntityWithRelations(dto);
        entity.setId(dto.getId());
        servicesRepository.save(entity);
        log.info("Services update with id: " + entity.getId());
    }

    private PhotoDTO updatePhoto(MultipartFile file, Long id, PhotoType photoType) {
        if (!file.isEmpty()) {
            PhotoDTO photoDTO = photoService.store(file, photoType);
            if (id != null){
                log.info("Updating " + photoType + " photo with id: " + id);
                PhotoDTO oldVersion = photoService.getById(id);
                photoService.deletePhotoFile(oldVersion.getUrl());
                photoService.update(id, photoDTO);
                return photoService.getById(id);
            }else {
                log.info("Saving " + photoType + " with id: " + id);
                return photoService.save(photoDTO);
            }
        }
        return id == null ? null : photoService.getById(id);
    }
}
