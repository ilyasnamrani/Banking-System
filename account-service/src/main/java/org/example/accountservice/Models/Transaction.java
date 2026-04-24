package org.example.accountservice.Models;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Setter @ToString  @AllArgsConstructor @NoArgsConstructor
public class Transaction {
    private Long idTransaction;
    private Long idFromAccount ;
    private Long idToAccount;
    private Double amount;
    private String status;
    private LocalTime time;

}
