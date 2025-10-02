package org.example.rent.controllers;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.PhotoDTO;
import org.example.rent.dto.ServicesDTO;
import org.example.rent.dto.ViewingRequestDTO;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.ServicesStatus;
import org.example.rent.other.ViewingRequestStatus;
import org.example.rent.services.ServicesService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/services")
public class ServicesController {

    public final ServicesService servicesService;
    private final Logger log = CustomLogger.getLog();

    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @GetMapping
    public String listServices(@RequestParam(required = false) String title,
                               @RequestParam(required = false) ServicesStatus status,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model) {

        Page<ServicesDTO> servicesPage = servicesService.getFilteredPage(title, status, page, size);

        model.addAttribute("servicesPage", servicesPage);
        model.addAttribute("title", title);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", servicesPage.getTotalPages());
        model.addAttribute("contentTemplate", "services/list");
        model.addAttribute("contentFragment", "content");
        return "layouts/base";
    }

    @GetMapping("/create")
    public String newService(Model model){
        model.addAttribute("contentTemplate", "services/new");
        model.addAttribute("contentFragment", "content");
        log.info("Creating Service");
        return "layouts/base";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        model.addAttribute("contentTemplate", "services/card");
        model.addAttribute("contentFragment", "content");
        ServicesDTO servicesDTO = servicesService.findById(id);
        model.addAttribute("services", servicesDTO);

        log.info("Getting Services with id " + id);
        return "layouts/base";
    }

    @PostMapping("/create")
    public String create(ServicesDTO dto, Model model) {
        servicesService.save(dto);
        log.info("Service was created");
        return "redirect:/services";
    }

    @PostMapping("/{id}/photos")
    public ResponseEntity<PhotoDTO> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        PhotoDTO dto = servicesService.savePhotoServices(id, file);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/status")
    public String toggleStatus(@PathVariable Long id) {
        servicesService.updateStatusServices(id);
        log.info("Services Controller toggleStatus with id: " + id);
        return "redirect:/services";
    }

    @PostMapping("/delete")
    public String deleteAll() {
        servicesService.deleteAll();
        log.info("Services delete all");
        return "redirect:/services";
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        servicesService.deleteById(id);
        log.info("Services delete with id: " + id);
        return "redirect:/services";
    }

}
