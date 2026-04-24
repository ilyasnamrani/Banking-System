package org.example.accountservice.services;

import org.example.accountservice.Models.Fraud;
import org.example.accountservice.Models.Transaction;
import org.example.accountservice.dtos.AccountEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class KafkaConsumer {
    private final AccountService accountService;
    private final KafkaService kafkaService;
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    public KafkaConsumer(AccountService accountService, KafkaService kafkaService) {
        this.accountService = accountService;
        this.kafkaService = kafkaService;
    }

    @KafkaListener(topics = "transaction-topic", groupId = "transaction-group")
    public void consumeTransactionEvent(Transaction transaction) {
        if ("TRANSACTION_INITIATED".equals(transaction.getStatus())) {
            AccountEvent accountEvent = new AccountEvent();
            accountEvent.setTransactionId(transaction.getIdTransaction());
            accountEvent.setAccountId(transaction.getIdFromAccount());
            accountEvent.setAmount(transaction.getAmount());
            accountEvent.setTime(LocalTime.now());
            try {
                accountService.debit(
                        transaction.getIdFromAccount(),
                        transaction.getAmount());
                accountEvent.setStatus("ACCOUNT_DEBITED");
                kafkaService.sendAccountDebited(accountEvent);

            } catch (Exception e) {
                accountEvent.setStatus("ACCOUNT_DEBIT_FAILED");
                kafkaService.sendAccountDebitFailed(accountEvent);
            }
        }
    }

    @KafkaListener(topics = "fraud-topic", groupId = "fraud-group")
    public void consumeFraudEvent(Fraud fraud) {

        AccountEvent accountEvent = new AccountEvent();
        accountEvent.setTransactionId(fraud.getIdTransaction());
        accountEvent.setAccountId(fraud.getIdAccount());
        accountEvent.setAmount(fraud.getAmount());
        accountEvent.setTime(LocalTime.now());

        if ("FRAUD_DETECTED".equals(fraud.getStatus())) {

            accountService.refund(
                    fraud.getIdAccount(),
                    fraud.getAmount());

            accountEvent.setStatus("ACCOUNT_REFUNDED");
            kafkaService.sendAccountRefunded(accountEvent);
        } else if ("FRAUD_NON_DETECTED".equals(fraud.getStatus())) {
            accountService.credit(
                    fraud.getIdAccount(),
                    fraud.getAmount());
            accountEvent.setStatus("ACCOUNT_CREDITED");
            kafkaService.sendAccountCredited(accountEvent);
        } else {
            logger.warn("Unknown fraud status received: {}", fraud.getStatus());
        }
    }

}
