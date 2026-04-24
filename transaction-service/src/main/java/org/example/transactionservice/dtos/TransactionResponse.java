package org.example.transactionservice.dtos;

import lombok.*;
import org.example.transactionservice.enums.Status;

import java.time.LocalTime;

@Getter @Setter @ToString  @AllArgsConstructor @NoArgsConstructor
public class TransactionResponse {
    private Long idTransaction;
    private Long idFromAccount ;
    private Long idToAccount;
    private Double amount;
    private Status status;
    private LocalTime time;

}