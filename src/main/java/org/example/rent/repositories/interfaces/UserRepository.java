package org.example.rent.repositories.interfaces;

import org.example.rent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
