package org.example.rent.repositories.interfaces;

import org.example.rent.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServicesRepository extends JpaRepository<Services, Long>,
        JpaSpecificationExecutor<Services> {
}
