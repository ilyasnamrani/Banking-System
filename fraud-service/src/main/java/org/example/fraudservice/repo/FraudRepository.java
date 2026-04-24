package org.example.fraudservice.repo;

import org.example.fraudservice.entities.Fraud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudRepository extends JpaRepository<Fraud,Long> {
}
