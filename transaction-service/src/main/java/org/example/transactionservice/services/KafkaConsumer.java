package org.example.transactionservice.services;

import org.example.transactionservice.dtos.TransactionResponse;
import org.example.transactionservice.models.AccountEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class KafkaConsumer {
      private final KafkaProducer kafkaProducer;
      public KafkaConsumer(KafkaProducer kafkaProducer) {
          this.kafkaProducer = kafkaProducer;
      }

    @KafkaListener(topics = "account-topic",groupId = "account-group")
    public void consumeAccountEvent(AccountEvent accountEvent){
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setIdTransaction(accountEvent.getTransactionId());
        transactionResponse.setTime(LocalTime.now());
        transactionResponse.setIdFromAccount(accountEvent.getAccountId());
        transactionResponse.setAmount(accountEvent.getAmount());
        if(accountEvent.getStatus().equals("ACCOUNT_DEBIT_FAILED")){
            kafkaProducer.sendTransactionFailed(transactionResponse);
        }
        else if(accountEvent.getStatus().equals("ACCOUNT_DEBITED")){
            kafkaProducer.sendTransactionInProgress(transactionResponse);
        }
        else if(accountEvent.getStatus().equals("ACCOUNT_CREDITED")){
             kafkaProducer.sendTransactionSucceed(transactionResponse);
        }else if(accountEvent.getStatus().equals("ACCOUNT_REFUNDED")){
            kafkaProducer.sendTransactionFailed(transactionResponse);
        }

    }
}
