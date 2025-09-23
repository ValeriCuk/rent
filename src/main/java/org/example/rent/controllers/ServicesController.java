package org.example.rent.controllers;

import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.ServicesDTO;
import org.example.rent.services.ServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServicesController {

    public final ServicesService servicesService;

    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @GetMapping
    public ResponseEntity<List<ServicesDTO>> getAll() {
        return ResponseEntity.ok(servicesService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServicesDTO> getById(@PathVariable Long id) {
        ServicesDTO servicesDTO = servicesService.findById(id);
        return ResponseEntity.ok(servicesDTO);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ServicesDTO dto) {
        servicesService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/photos")
    public ResponseEntity<PhotoDTO> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        PhotoDTO dto = servicesService.savePhotoServices(id, file);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void>  updateStatus(@PathVariable Long id, @RequestBody String status) {
        servicesService.updateStatusServices(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        servicesService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        servicesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
