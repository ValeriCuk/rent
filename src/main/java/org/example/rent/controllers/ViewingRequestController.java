package org.example.rent.controllers;

import org.example.rent.dto.UserDTO;
import org.example.rent.dto.ViewingRequestDTO;
import org.example.rent.other.ViewingRequestStatus;
import org.example.rent.services.ViewingRequestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/viewings")
public class ViewingRequestController {

    private final ViewingRequestService viewingRequestService;

    private final List<ViewingRequestDTO> testCollection = List.of(
            new ViewingRequestDTO(0L, "Comment A", ViewingRequestStatus.NEW, Date.valueOf(LocalDate.now()), new UserDTO("Bob Smith", "bob@mail.com", "+380662584400"), null),
            new ViewingRequestDTO(1L, "Comment A", ViewingRequestStatus.NEW, Date.valueOf(LocalDate.now()), new UserDTO("Glen Smith", "bob@mail.com", "+380662584400"), null),
            new ViewingRequestDTO(2L, "Comment D", ViewingRequestStatus.NEW, Date.valueOf(LocalDate.now()), new UserDTO("Barb Smith", "bob@mail.com", "+380662584400"), null),
            new ViewingRequestDTO(3L, "Comment D", ViewingRequestStatus.PROCESSED, Date.valueOf(LocalDate.now()), new UserDTO("Tom Smith", "bob@mail.com", "+380662584400"), null),
            new ViewingRequestDTO(4L, "Comment A", ViewingRequestStatus.NEW, Date.valueOf(LocalDate.now()), new UserDTO("Holy Smith", "bob@mail.com", "+380662584400"), null),
            new ViewingRequestDTO(5L, "Comment B", ViewingRequestStatus.PROCESSED, Date.valueOf(LocalDate.now()), new UserDTO("Jessy Smith", "bob@mail.com", "+380662584400"), null)
    );

    public ViewingRequestController(ViewingRequestService viewingRequestService) {
        this.viewingRequestService = viewingRequestService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("contentTemplate", "viewings/list");
        model.addAttribute("contentFragment", "content");
//        model.addAttribute("viewings", viewingRequestService.getAll());
        model.addAttribute("viewings", testCollection);
        return "layouts/base";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        model.addAttribute("contentTemplate", "viewings/card");
        model.addAttribute("contentFragment", "content");
//        ViewingRequestDTO viewingRequestDTO = viewingRequestService.getById(id);
        ViewingRequestDTO viewingRequestDTO = testCollection.parallelStream().filter(viewingRequest -> Objects.equals(viewingRequest.getId(), id)).findFirst().orElse(null);
        model.addAttribute("viewing", viewingRequestDTO);
        return "layouts/base";
    }




    @GetMapping("/export")
    public ResponseEntity<byte[]> exportList() {
        byte[] file = viewingRequestService.exportToExcel();
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

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        viewingRequestService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        viewingRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void>  updateStatus(@PathVariable Long id, @RequestBody String status) {
        viewingRequestService.updateStatusViewingRequest(id, status);
        return ResponseEntity.noContent().build();
    }
}
