package org.example.rent.repositories.interfaces.properties;

import org.example.rent.entity.property.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
