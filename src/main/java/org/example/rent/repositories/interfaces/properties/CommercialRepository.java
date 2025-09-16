package org.example.rent.repositories.interfaces.properties;

import org.example.rent.entity.property.Commercial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommercialRepository extends JpaRepository<Commercial, Long> {
}
