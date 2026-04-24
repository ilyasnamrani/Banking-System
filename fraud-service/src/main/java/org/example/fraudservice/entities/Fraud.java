package org.example.fraudservice.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.fraudservice.Status;

import java.time.LocalTime;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Builder @Entity
public class Fraud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  idFraud;
    private Long idTransaction ;
    private Status status;
    private Long idAccount;
    private Double amount ;
    private LocalTime time;
}
