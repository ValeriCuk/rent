package org.example.rent.repositories.interfaces;

import org.example.rent.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
