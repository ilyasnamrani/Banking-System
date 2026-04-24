package org.example.fraudservice.models;


import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountEvent {
    private Long transactionId ;
    private  Long accountId ;
    private Double amount ;
    private String status ;
    private LocalTime time;
}
