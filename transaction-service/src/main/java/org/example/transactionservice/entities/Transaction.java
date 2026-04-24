package org.example.transactionservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.transactionservice.enums.Status;

import java.time.LocalTime;

@Getter @Setter @ToString  @AllArgsConstructor @NoArgsConstructor
@Entity @Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;
    private Long idFromAccount ;
    private Long idToAccount;
    private Double amount;
    private Status status;
    private LocalTime time;

}
