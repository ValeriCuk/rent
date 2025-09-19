package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.ViewingRequestDTO;
import org.example.rent.entity.ViewingRequest;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.ViewingRequestRepository;
import org.example.rent.services.mappers.ViewingRequestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ViewingRequestService {

    private final ViewingRequestRepository viewingRequestRepository;
    private final ViewingRequestMapper viewingRequestMapper;
    private final Logger log = CustomLogger.getLog();

    public ViewingRequestService(ViewingRequestRepository viewingRequestRepository, ViewingRequestMapper viewingRequestMapper) {
        this.viewingRequestRepository = viewingRequestRepository;
        this.viewingRequestMapper = viewingRequestMapper;
    }

    //getById(Long id)
    public ViewingRequestDTO getById(Long id) {
        ViewingRequest viewingRequest = viewingRequestRepository.findById(id).orElseThrow(() -> new NotFoundException("Order with id: " + id + " not found"));
        log.info("Get order with id: " + id);
        return viewingRequestMapper.toDtoWithRelations(viewingRequest);
    }

    //getAll()
    public List<ViewingRequestDTO> getAll() {
        List<ViewingRequest> viewingRequests = viewingRequestRepository.findAll();
        log.info("Get all orders: " + viewingRequests);
        return viewingRequests.stream().map(viewingRequestMapper::toDtoWithRelations).collect(Collectors.toList());
    }

    //save(DTO dto)
    @Transactional
    public void save(ViewingRequestDTO viewingRequestDTO) {
        ViewingRequest viewingRequest = viewingRequestMapper.toEntityWithRelations(viewingRequestDTO);
        viewingRequestRepository.save(viewingRequest);
        log.info("Save order with id: " + viewingRequest.getId());
    }

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        if (!viewingRequestRepository.existsById(id))
            throw new NotFoundException("Order with id: " + id + " not found");
        viewingRequestRepository.deleteById(id);
        log.info("Delete order with id: " + id);
    }

    //update(Long id, DTO dto)
    @Transactional
    public void update(Long id, ViewingRequestDTO viewingRequestDTO) {
        if (!viewingRequestRepository.existsById(id))
            throw new NotFoundException("Order with id: " + id + " not found");
        ViewingRequest viewingRequest = viewingRequestMapper.toEntityWithRelations(viewingRequestDTO);
        viewingRequest.setId(id);
        viewingRequestRepository.save(viewingRequest);
        log.info("Update order with id: " + id);
    }
}
