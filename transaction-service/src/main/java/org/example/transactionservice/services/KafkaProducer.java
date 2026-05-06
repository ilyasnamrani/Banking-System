package org.example.transactionservice.services;

import org.example.transactionservice.dtos.TransactionResponse;
import org.example.transactionservice.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

    @Service
    public class KafkaProducer {

        @Value("${spring.kafka.topic.name}")
        private String topicName;
        public KafkaTemplate<String, TransactionResponse> kafkaTemplate;
        private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

        public KafkaProducer(KafkaTemplate<String, TransactionResponse> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
        }
        public void  sendTransactionRequested(TransactionResponse transactionResponse){
            transactionResponse.setStatus(Status.TRANSACTION_INITIATED);
            kafkaTemplate.send(topicName,String.valueOf(transactionResponse.getIdTransaction()),transactionResponse)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info(" Message{} sent    successfully to the  topic {} [Partition: {}, Offset: {}]", transactionResponse.getStatus(), topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                        } else {
                            logger.error("Fail to send  message to the topic {} : {}", topicName, ex.getMessage());
                        }
                    });
        }

        public void  sendTransactionSucceed(TransactionResponse transactionResponse){
            transactionResponse.setStatus(Status.TRANSACTION_SUCCESS);
            kafkaTemplate.send(topicName,String.valueOf(transactionResponse.getIdTransaction()),transactionResponse)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info(" Message{} sent successfully to the  topic {} [Partition: {}, Offset: {}]", transactionResponse.getStatus(), topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                        } else {
                            logger.error("Fail to send   message to the topic {} : {}", topicName, ex.getMessage());
                        }
                    });
        }

        public void  sendTransactionInProgress(TransactionResponse transactionResponse){
            transactionResponse.setStatus(Status.TRANSACTION_IN_PROGRESS);
            kafkaTemplate.send(topicName,String.valueOf(transactionResponse.getIdTransaction()),transactionResponse)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("  Message{} sent successfully to the  topic {} [Partition: {}, Offset: {}]", transactionResponse.getStatus(), topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                        } else {
                            logger.error(" Fail to send   message to the topic {} : {}", topicName, ex.getMessage());
                        }
                    });
        }

        public void  sendTransactionFailed(TransactionResponse transactionResponse){
            transactionResponse.setStatus(Status.TRANSACTION_FAILED);
            kafkaTemplate.send(topicName,String.valueOf(transactionResponse.getIdTransaction()),transactionResponse)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info(" Message{} sent  successfully to the  topic {} [Partition: {}, Offset: {}]", transactionResponse.getStatus(), topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                        } else {
                            logger.error("Fail to send message to the topic {} : {}", topicName, ex.getMessage());
                        }
                    });
        }




    }
