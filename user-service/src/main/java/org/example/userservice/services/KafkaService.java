package org.example.userservice.services;

import org.example.userservice.dtos.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    private final KafkaTemplate<String, UserResponse> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, UserResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCandidateCreatedEvent(UserResponse user) {
        user.setEventType("CREATED");
        kafkaTemplate.send(topicName, user.getIdUser().toString(), user)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message CREATED envoyé avec succès au topic {} [Partition: {}, Offset: {}]",
                                topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        logger.error("Échec de l'envoi du message au topic {} : {}", topicName, ex.getMessage());
                    }
                });
    }

    public void sendCandidateUpdatedEvent(UserResponse user) {
        user.setEventType("UPDATED");
        kafkaTemplate.send(topicName, user.getIdUser().toString(), user)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message UPDATED envoyé avec succès au topic {} [Partition: {}, Offset: {}]",
                                topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        logger.error("Échec de l'envoi du message de mise à jour au topic {} : {}", topicName, ex.getMessage());
                    }
                });
    }

    public void sendCandidateDeletedEvent(Long idUser) {
        kafkaTemplate.send(topicName, String.valueOf(idUser), null)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.info("Message DELETED envoyé avec succès au topic {} [Partition: {}, Offset: {}]",
                                topicName, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        logger.error("Échec de l'envoi du message de suppression au topic {} : {}", topicName, ex.getMessage());
                    }
                });
    }

}
