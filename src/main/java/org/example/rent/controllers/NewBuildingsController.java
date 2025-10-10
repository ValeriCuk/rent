package org.example.rent.controllers;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.other.BuildingStatus;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.ServicesStatus;
import org.example.rent.services.BuildingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/new-buildings")
public class NewBuildingsController {

    private BuildingService buildingService;
    private final Logger log = CustomLogger.getLog();

    public NewBuildingsController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping
    public String getAll(
            @RequestParam(required = false) String title,
            @ModelAttribute AddressDTO address,
            @RequestParam(required = false) BuildingStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Page<BuildingDTO> buildingPage = buildingService.getFilteredPage(title, address, status, page, size);

        model.addAttribute("buildingPage", buildingPage);
        model.addAttribute("title", title);
        model.addAttribute("address", address);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", buildingPage.getTotalPages());

        model.addAttribute("contentTemplate", "buildings/list");
        model.addAttribute("contentFragment", "content");
        return "layouts/base";
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
