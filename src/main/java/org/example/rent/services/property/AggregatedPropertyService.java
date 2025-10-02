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

        log.info("getFilteredPage, propertyServices");

        List<CompletableFuture<? extends List<? extends PropertyDTO>>> futures = propertyServices.stream()
                .map(service -> (CompletableFuture<List<PropertyDTO>>) service.getAllAsync())
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<PropertyDTO> all = futures.stream()
                .flatMap(f -> f.join().stream())
                .sorted(Comparator.comparing(PropertyDTO::getId))
                .collect(Collectors.toList());

        try {
            System.out.println("type " + type);
        } catch (NullPointerException e) {
            System.out.println("exception NullPointerException type");
        }
        try {
            System.out.println("id " + id);
        } catch (NullPointerException e) {
            System.out.println("exception NullPointerException id");
        }
        try {
            System.out.println("bedrooms " + bedrooms);
        } catch (NullPointerException e) {
            System.out.println("exception NullPointerException bedrooms");
        }

        log.info(all.size() + " -> Before filtered in AggregatedPropertyService " + all);

        List<PropertyDTO> filtered = all.stream()
                .filter(p -> id == null || p.getId().equals(id))
                .filter(p -> type == null || p.getTypeDisplay().equals(type.name()))
                .filter(p -> bedrooms == null || bedrooms.isBlank() || p.getBedroomsDisplay().equals(bedrooms))
                .collect(Collectors.toList());

        log.info(filtered.size() + " -> After filtered before pagination in AggregatedPropertyService " + filtered);

        int start = page * size;
        int end = Math.min(start + size, filtered.size());
        if (start >= filtered.size()) {
            log.warn("No more pages in AggregatedPropertyService start >= filtered.size()");
            start = 0;
            end = Math.min(size, filtered.size());
        }

        List<PropertyDTO> paginated = filtered.subList(start, end);

        log.info(paginated.size() + " -> After filtered after pagination in AggregatedPropertyService " + paginated);
        return new PageImpl<>(paginated, PageRequest.of(page, size), filtered.size());
    }
}
