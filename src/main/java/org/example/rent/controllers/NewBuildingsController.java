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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        model.addAttribute("activeTab", "main");

        return "layouts/base";
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
