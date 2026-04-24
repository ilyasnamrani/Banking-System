package org.example.accountservice.services;

import org.example.accountservice.Models.Fraud;
import org.example.accountservice.Models.Transaction;
import org.example.accountservice.dtos.AccountEvent;
import org.example.accountservice.dtos.AccountResponse;
import org.example.accountservice.entities.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class KafkaService {

    @Value("${spring.kafka.topic.name}")
    private String topicName;
     public KafkaTemplate<String, AccountEvent> kafkaTemplate;
     private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

     public KafkaService(KafkaTemplate<String, AccountEvent> kafkaTemplate) {
         this.kafkaTemplate = kafkaTemplate;
     }
    public void  sendAccountDebited(AccountEvent event){
          kafkaTemplate.send(topicName,String.valueOf(event.getAccountId()),event)
                  .whenComplete((result, ex) -> {
                      if (ex == null) {
                          logger.info("Message ACCOUNT_DEBITED sent successfully to the  topic {} [Partition: {}, Offset: {}]",
                                  topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                      } else {
                          logger.error("Fail to send message to the topic {} : {}", topicName, ex.getMessage());
                      }
                  });
    }

    public void  sendAccountDebitFailed(AccountEvent event){
        kafkaTemplate.send(topicName,String.valueOf(event.getTransactionId()),event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message ACCOUNT_DEBIT_FAILED sent successfully to topic {} [Partition: {}, Offset: {}]",
                                topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        logger.error("fail to send message to the topic {} : {}", topicName, ex.getMessage());
                    }
                });
    }

    public void  sendAccountCredited(AccountEvent event){
        kafkaTemplate.send(topicName,String.valueOf(event.getTransactionId()),event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message ACCOUNT_CREDITED envoyé avec succès au topic {} [Partition: {}, Offset: {}]",
                                topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        logger.error("Échec de l'envoi du message  au topic {} : {}", topicName, ex.getMessage());
                    }
                });
    }

    public void  sendAccountRefunded(AccountEvent event){
        kafkaTemplate.send(topicName,String.valueOf(event.getTransactionId()),event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message ACCOUNT_REFUNDED envoyé avec succès au topic {} [Partition: {}, Offset: {}]",
                                topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        logger.error("Échec de l'envoi du  message  au topic {} : {}", topicName, ex.getMessage());
                    }
                });
    }



}
