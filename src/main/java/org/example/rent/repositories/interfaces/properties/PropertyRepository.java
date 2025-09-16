package org.example.rent.repositories.interfaces.properties;

import org.example.rent.entity.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
