package org.example.rent.repositories.interfaces.properties;

import org.example.rent.entity.property.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
