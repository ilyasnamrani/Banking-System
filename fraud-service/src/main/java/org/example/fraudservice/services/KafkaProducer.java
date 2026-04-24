package org.example.fraudservice.services;


import org.example.fraudservice.Status;
import org.example.fraudservice.entities.Fraud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Value("${spring.kafka.topic.name}")
    private String topicName;
    public KafkaTemplate<String, Fraud> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    public KafkaProducer(KafkaTemplate<String, Fraud> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFraudEvent(Fraud event) {
        kafkaTemplate.send(topicName, String.valueOf(event.getIdFraud()), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message {} sent successfully to the  topic {} [Partition: {}, Offset: {}]", event.getStatus(),
                                topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        logger.error("Fail to send message to the topic {} : {}", topicName, ex.getMessage());
                    }
                });
    }


}
