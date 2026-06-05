package org.example.fraudservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.fraudservice.entities.Fraud;
import org.example.fraudservice.repo.FraudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FraudServiceImp implements FraudService{

    private final FraudRepository fraudRepository;

    public FraudServiceImp(FraudRepository fraudRepository) {
        this.fraudRepository = fraudRepository;
    }


    @Override
    @Transactional
    public Fraud createFraud(Fraud fraud) {
        return fraudRepository.save(fraud);
    }

    @Override
    @Transactional
    public void deleteFraud(Long id) {
          fraudRepository.deleteById(id);
    }

    @Override
    public Boolean isFraud(Double amount) {
        return amount>1000.00;
    }

    @Override
    public Fraud showFraud(Long id) {
        return fraudRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Fraud not found !!")
        );
    }


    @Override
    public List<Fraud> showAllFrauds() {
        return new ArrayList<>(fraudRepository.findAll());
    }

    @Override
    public List<Fraud> showAllFraudsByAccount(Long id) {
        return new ArrayList<>(fraudRepository.findByIdAccount(id));
    }
}
