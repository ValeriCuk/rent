package org.example.rent.repositories.interfaces;

import org.example.rent.entity.ViewingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewingRequestRepository extends JpaRepository<ViewingRequest, Long> {
}
