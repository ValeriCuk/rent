package org.example.rent.controllers;

import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.services.BuildingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/new-buildings")
public class NewBuildingsController {

    private BuildingService buildingService;

    public NewBuildingsController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping
    public ResponseEntity<List<BuildingDTO>> getAll() {
        return ResponseEntity.ok(buildingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingDTO> getById(@PathVariable Long id) {
        BuildingDTO buildingDTO = buildingService.getById(id);
        return ResponseEntity.ok(buildingDTO);
    }

    @PostMapping
    public ResponseEntity<BuildingDTO> create(@RequestBody BuildingDTO buildingDTO) {
        buildingService.save(buildingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        buildingService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        buildingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void>  updateStatus(@PathVariable Long id, @RequestBody String status) {
        buildingService.updateStatusBuilding(id, status);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/photos")
    public ResponseEntity<PhotoDTO> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        PhotoDTO dto = buildingService.storePhotoBuilding(id, file);
        return ResponseEntity.ok(dto);

    }
}
