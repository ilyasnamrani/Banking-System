package org.example.fraudservice.repo;

import org.example.fraudservice.entities.Fraud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FraudRepository extends JpaRepository<Fraud,Long> {
    List<Fraud> findByIdAccount(Long id);
}
