package org.example.rent.controllers;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.ServicesDTO;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.ServicesStatus;
import org.example.rent.services.ServicesService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        log.info("listServices get, title: " + title + ", status: " + status);
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

    @GetMapping("/{id}/editing")
    public String getById(@PathVariable Long id, Model model) {
        model.addAttribute("contentTemplate", "services/card");
        model.addAttribute("contentFragment", "content");
        ServicesDTO servicesDTO = servicesService.findById(id);
        log.info("PHOTO size " +  servicesDTO.getPhotos().size());
        log.info("Banner DTO " + servicesDTO.getBannerPhotoDTO());
        log.info("Preview DTO " + servicesDTO.getPreviewPhotoDTO());

        model.addAttribute("service", servicesDTO);

        log.info("Getting Services with id " + id);
        return "layouts/base";
    }

    @PostMapping("/save")
    public String create(ServicesDTO dto, Model model) {
//        servicesService.updateWithPhoto(dto, bannerId);
        log.info("Service was created");
        return "redirect:/services";
    }

    @PostMapping("/update")
    public String update(ServicesDTO dto, Model model,
                         @RequestParam(value = "bannerId", required = false) Long bannerId,
                         @RequestParam(value = "previewId", required = false) Long previewId) {
        servicesService.updateWithPhoto(dto, bannerId, previewId);
        log.info("Service was changed");
        return "redirect:/services";
    }

    @PostMapping("/{id}/status")
    public String toggleStatus(@PathVariable Long id) {
        log.info("Services Controller toggleStatus with id: " + id);
        servicesService.updateStatusServices(id);
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
