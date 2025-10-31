package org.example.rent.controllers;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.AddressDTO;
import org.example.rent.dto.BuildingDTO;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.other.BuildingStatus;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.PhotoType;
import org.example.rent.services.BuildingService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/new-buildings")
public class NewBuildingsController {

    private final BuildingService buildingService;
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

    @GetMapping("/{id}/editing")
    public String getById(@PathVariable Long id, Model model) {
        BuildingDTO buildingDTO = buildingService.getById(id);
        String bannerURL = buildingService.getURLPhoto(buildingDTO, PhotoType.BANNER);

        model.addAttribute("contentTemplate", "buildings/card");
        model.addAttribute("contentFragment", "content");
        model.addAttribute("building", buildingDTO);
        model.addAttribute("banner", bannerURL);

        return "layouts/base";
    }

    @GetMapping("/{id}/tab/{tab}")
    public String loadTab(@PathVariable Long id, @PathVariable String tab, Model model) {
        BuildingDTO dto = buildingService.getById(id);
        String bannerURL = buildingService.getURLPhoto(dto, PhotoType.BANNER);
        List<String> projectPhotos = buildingService.getURLsPhoto(dto, PhotoType.B_PROJECT);
        List<String> infraPhotos = buildingService.getURLsPhoto(dto, PhotoType.B_INFRASTRUCTURE);
        List<String> apartmentsPhotos = buildingService.getURLsPhoto(dto, PhotoType.B_APARTMENTS);
        String panoramaURL = buildingService.getURLPhoto(dto, PhotoType.PANORAMA);

        model.addAttribute("building", dto);
        model.addAttribute("banner", bannerURL);
        model.addAttribute("projectPhotos", projectPhotos);
        model.addAttribute("location", dto.getLocation());
        model.addAttribute("infraPhotos", infraPhotos);
        model.addAttribute("apartmentsPhotos", apartmentsPhotos);
        model.addAttribute("panorama", panoramaURL);

        return switch (tab) {
            case "project" -> "buildings/tab-project :: content";
            case "location" -> "buildings/tab-location :: content";
            case "infra" -> "buildings/tab-infra :: content";
            case "apartments" -> "buildings/tab-apartments :: content";
            case "panorama" -> "buildings/tab-panorama :: content";
            case "specification" -> "buildings/tab-specification :: content";
            default -> "buildings/tab-main :: content";
        };
    }

//    @PostMapping
//    public ResponseEntity<BuildingDTO> create(@RequestBody BuildingDTO buildingDTO) {
//        buildingService.save(buildingDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping("/delete")
    public String deleteAll() {
        buildingService.deleteAll();
        return "redirect:/new-buildings";
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        buildingService.delete(id);
        return "redirect:/new-buildings";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id) {
        buildingService.updateStatusBuilding(id);
        return "redirect:/new-buildings";
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
