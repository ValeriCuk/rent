package org.example.rent.repositories.interfaces;

import org.example.rent.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
