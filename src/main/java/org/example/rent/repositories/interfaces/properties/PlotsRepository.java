package org.example.rent.repositories.interfaces.properties;

import org.example.rent.entity.property.Plots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlotsRepository extends JpaRepository<Plots, Long> {
}
