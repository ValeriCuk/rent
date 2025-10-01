package org.example.rent.repositories.interfaces;

import org.example.rent.entity.ViewingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ViewingRequestRepository extends JpaRepository<ViewingRequest, Long>,
        JpaSpecificationExecutor<ViewingRequest> {
}
