package org.example.rent.controllers;

import org.example.rent.dto.ViewingRequestDTO;
import org.example.rent.services.ViewingRequestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viewings")
public class ViewingRequestController {

    private final ViewingRequestService viewingRequestService;

    public ViewingRequestController(ViewingRequestService viewingRequestService) {
        this.viewingRequestService = viewingRequestService;
    }

    @GetMapping
    public ResponseEntity<List<ViewingRequestDTO>> getAll() {
        return ResponseEntity.ok(viewingRequestService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewingRequestDTO> getById(@PathVariable Long id) {
        ViewingRequestDTO viewingRequestDTO = viewingRequestService.getById(id);
        return ResponseEntity.ok(viewingRequestDTO);
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
