package org.example.fraudservice.services;

import org.example.fraudservice.entities.Fraud;

import java.util.List;

public interface FraudService {
    public Fraud createFraud(Fraud fraud);
    public void deleteFraud(Long id);
    public Boolean isFraud(Double amount);
    public List<Fraud> showAllFrauds();
    public List<Fraud> showAllFraudsByAccount(Long id);
    public Fraud showFraud(Long id);


}
