package org.example.rent.controllers;

import org.example.rent.dto.PhotoDTO;
import org.example.rent.entity.property.Property;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.services.property.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final ApartmentService apartmentService;
    private final CommercialService commercialService;
    private final HouseService houseService;
    private final PlotsService plotsService;

    private final List<PropertyService<? extends Property, ? extends PropertyDTO>> propertyServices;

    public PropertyController(ApartmentService apartmentService,
                              CommercialService commercialService,
                              HouseService houseService,
                              PlotsService plotsService) {
        this.apartmentService = apartmentService;
        this.commercialService = commercialService;
        this.houseService = houseService;
        this.plotsService = plotsService;
        this.propertyServices = List.of(apartmentService, commercialService, houseService, plotsService);
    }

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAll() {
        List<PropertyDTO> properties = new ArrayList<>();
        propertyServices.forEach(service -> properties.addAll(service.getAll()));
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getById(@PathVariable Long id) {
        PropertyDTO body = null;
        for (PropertyService<? extends Property, ? extends PropertyDTO> service : propertyServices) {
            try {
                PropertyDTO dto = service.getById(id);
                return ResponseEntity.ok(dto);
            } catch (NotFoundException ignored) {
                continue;
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        for (PropertyService<? extends Property, ? extends PropertyDTO> service : propertyServices) {
            service.deleteAll();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{propertyId}/photos")
    public ResponseEntity<PhotoDTO> uploadPhoto(@PathVariable Long propertyId,@RequestParam("file") MultipartFile file) {
        for (PropertyService<? extends Property, ? extends PropertyDTO> service : propertyServices) {
            try {
                PhotoDTO dto = service.storePhotoProperty(propertyId, file);
                return ResponseEntity.ok(dto);
            } catch (NotFoundException ignored) {
                continue;
            }
        }
        return ResponseEntity.notFound().build();
    }
}
