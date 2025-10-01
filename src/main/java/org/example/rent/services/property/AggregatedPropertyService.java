package org.example.rent.services.property;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.propertydto.PropertyDTO;
import org.example.rent.other.CustomLogger;
import org.example.rent.other.PropertyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AggregatedPropertyService {

    private final List<PropertyService> propertyServices;
    private final Logger log = CustomLogger.getLog();

    public AggregatedPropertyService(List<PropertyService> propertyServices) {
        this.propertyServices = propertyServices;
    }

    public Page<PropertyDTO> getFilteredPage(Long id, PropertyType type, String bedrooms, int page, int size) {
        log.debug("getFilteredPage, propertyServices.size -> " + propertyServices.size());
        List<CompletableFuture<? extends List<? extends PropertyDTO>>> futures = propertyServices.stream()
                .map(service -> (CompletableFuture<List<PropertyDTO>>) service.getAllAsync())
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<PropertyDTO> all = futures.stream()
                .flatMap(f -> f.join().stream())
                .sorted(Comparator.comparing(PropertyDTO::getId))
                .collect(Collectors.toList());

        List<PropertyDTO> filtered = all.stream()
                .filter(p -> id == null || p.getId().equals(id))
                .filter(p -> type == null || p.getTypeDisplay().equals(type.name()))
                .filter(p -> bedrooms == null || p.getBedroomsDisplay().equals(bedrooms))
                .collect(Collectors.toList());

        int start = page * size;
        int end = Math.min(start + size, filtered.size());
        if (start >= filtered.size()) {
            start = 0;
            end = Math.min(size, filtered.size());
        }

        List<PropertyDTO> paginated = filtered.subList(start, end);
        return new PageImpl<>(paginated, PageRequest.of(page, size), filtered.size());
    }
}
