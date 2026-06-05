package org.example.userservice.repo;

import org.example.userservice.dtos.UserResponse;
import org.example.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByCin(String cin);

    User findByKeycloakId(String keycloakId);
}
