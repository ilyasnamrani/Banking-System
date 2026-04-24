package org.example.fraudservice.services;

import org.example.fraudservice.entities.Fraud;

import java.util.List;

public interface FraudService {
    public Fraud createFraud(Fraud fraud);
    public void deleteFraud(Long id);
    public Boolean isFraud(Double amount);
    public Fraud showFraud(Long id);
    public Fraud showFraudByAccount(Long id,Long idUser);
    public List<Fraud> showAllFrauds();

}
