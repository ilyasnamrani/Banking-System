package org.example.fraudservice.services;

import org.example.fraudservice.Status;
import org.example.fraudservice.entities.Fraud;
import org.example.fraudservice.models.AccountEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final FraudService fraudService ;
    private final KafkaProducer kafkaProducer;
    public KafkaConsumer(FraudService fraudService, KafkaProducer kafkaProducer) {
        this.fraudService = fraudService;
        this.kafkaProducer = kafkaProducer;
    }
    @KafkaListener(topics = "account-topic", groupId = "fraud-group")
    public void consumeAccountEvent(AccountEvent accountEvent) {

        if ("ACCOUNT_DEBITED".equals(accountEvent.getStatus())) {

            Fraud fraud = new Fraud();

            fraud.setIdAccount(accountEvent.getAccountId());
            fraud.setAmount(accountEvent.getAmount());
            fraud.setIdTransaction(accountEvent.getTransactionId());
            fraud.setTime(LocalTime.now());

            if (fraudService.isFraud(accountEvent.getAmount())) {
                fraud.setStatus(Status.FRAUD_DETECTED);
            } else {
                fraud.setStatus(Status.FRAUD_NON_DETECTED);
            }
            kafkaProducer.sendFraudEvent(fraudService.createFraud(fraud));
        }
    }


}
