package org.example.accountservice.Models;

import lombok.*;

import java.time.LocalTime;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class Fraud {
    private Long  idFraud;
    private Long idTransaction ;
    private String status;
    private Long idAccount;
    private Double amount ;
    private LocalTime time;
}
