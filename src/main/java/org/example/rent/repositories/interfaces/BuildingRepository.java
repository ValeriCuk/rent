package org.example.rent.repositories.interfaces;

import org.example.rent.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BuildingRepository extends JpaRepository<Building, Long>,
        JpaSpecificationExecutor<Building> {
}
