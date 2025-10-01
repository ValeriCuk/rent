package org.example.rent.controllers;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.ViewingRequestDTO;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.ViewingRequestStatus;
import org.example.rent.services.ViewingRequestService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/viewings")
public class ViewingRequestController {

    private final ViewingRequestService viewingRequestService;
    private final Logger log = CustomLogger.getLog();

    public ViewingRequestController(ViewingRequestService viewingRequestService) {
        this.viewingRequestService = viewingRequestService;
    }

    @GetMapping
    public String listViewings(@RequestParam(required = false) String username,
                               @RequestParam(required = false) String phone,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) ViewingRequestStatus status,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model) {

        Page<ViewingRequestDTO> viewingsPage = viewingRequestService.getFilteredPage(username, phone, email, status, page, size);

        model.addAttribute("viewingsPage", viewingsPage);
        model.addAttribute("username", username);
        model.addAttribute("phone", phone);
        model.addAttribute("email", email);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", viewingsPage.getTotalPages());
        model.addAttribute("contentTemplate", "viewings/list");
        model.addAttribute("contentFragment", "content");
        return "layouts/base";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        model.addAttribute("contentTemplate", "viewings/card");
        model.addAttribute("contentFragment", "content");
        ViewingRequestDTO viewingRequestDTO = viewingRequestService.getById(id);
        model.addAttribute("viewing", viewingRequestDTO);

        log.info("ViewingRequestController getById with id: " + id);
        return "layouts/base";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportList() {
        byte[] file = viewingRequestService.exportToExcel();
        log.info("ViewingRequestController exportList with export");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=viewingRequests.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ViewingRequestDTO dto) {
        viewingRequestService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public String deleteAll() {
        viewingRequestService.deleteAll();
        log.info("ViewingRequestController delete all");
        return "redirect:/viewings";
    }

    @PostMapping("/{id}/delete")
    public String deleteById(@PathVariable Long id) {
        viewingRequestService.delete(id);
        log.info("ViewingRequestController delete with id: " + id);
        return "redirect:/viewings";
    }

    @PostMapping("/{id}/status")
    public String toggleStatus(@PathVariable Long id) {
        viewingRequestService.updateStatusViewingRequest(id);
        log.info("ViewingRequestController toggleStatus with id: " + id);
        return "redirect:/viewings";
    }

}
