package org.example.rent.repositories.interfaces.properties;

import org.example.rent.entity.property.Property;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PropertyRepository<T extends Property> extends JpaRepository<T, Long> {

    @NonNull
    @EntityGraph(attributePaths = {"address", "building", "building.photos", "building.address", "photos"})
    List<T> findAll();
}
