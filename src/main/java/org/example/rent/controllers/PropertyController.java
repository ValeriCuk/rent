package org.example.rent.controllers;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.propertydto.*;
import org.example.rent.entity.property.Property;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.PropertyType;
import org.example.rent.services.property.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final AggregatedPropertyService aggregatedPropertyService;
    private final ApartmentService apartmentService;
    private final CommercialService commercialService;
    private final HouseService houseService;
    private final PlotsService plotsService;
    private final Logger log = CustomLogger.getLog();

    private final List<PropertyService<? extends Property, ? extends PropertyDTO>> propertyServices;

    public PropertyController(ApartmentService apartmentService,
                              CommercialService commercialService,
                              HouseService houseService,
                              PlotsService plotsService,
                              AggregatedPropertyService aggregatedPropertyService) {
        this.apartmentService = apartmentService;
        this.commercialService = commercialService;
        this.houseService = houseService;
        this.plotsService = plotsService;
        this.propertyServices = List.of(apartmentService, commercialService, houseService, plotsService);
        this.aggregatedPropertyService = aggregatedPropertyService;
    }
    @GetMapping
    public String listProperties(@RequestParam(required = false) Long id,
                                 @RequestParam(required = false) PropertyType type,
                                 @RequestParam(required = false) String bedrooms,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 Model model) {

        Page<PropertyDTO> propertiesPage = aggregatedPropertyService.getFilteredPage(id, type, bedrooms, page, size);

        model.addAttribute("properties", propertiesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", propertiesPage.getTotalPages());
        model.addAttribute("id", id != null ? id.toString() : null);
        model.addAttribute("type", type != null ? type.name() : null);
        model.addAttribute("bedrooms", bedrooms);
        model.addAttribute("contentTemplate", "properties/list");
        model.addAttribute("contentFragment", "content");

        return "layouts/base";
    }


    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        PropertyDTO dto = null;
        for (PropertyService<? extends Property, ? extends PropertyDTO> service : propertyServices) {
            try {
                dto = service.getById(id);
            } catch (NotFoundException ignored) {
                continue;
            }
        }
        model.addAttribute("contentTemplate", "properties/card");
        model.addAttribute("contentFragment", "content");
        model.addAttribute("property", dto);
        return "layouts/base";
    }

    @PostMapping("/deleteAll")
    public String deleteAll() {
        propertyServices.forEach(PropertyService::deleteAll);
        return "redirect:/properties";
    }

    @PostMapping("/{propertyId}/photos")
    public String uploadPhoto(@PathVariable Long propertyId,
                              @RequestParam("file") MultipartFile file) {
        for (PropertyService<? extends Property, ? extends PropertyDTO> service : propertyServices) {
            try {
                service.storePhotoProperty(propertyId, file);
                return "redirect:/properties/" + propertyId;
            } catch (NotFoundException ignored) {
                continue;
            }
        }
        return "redirect:/properties";
    }
}
