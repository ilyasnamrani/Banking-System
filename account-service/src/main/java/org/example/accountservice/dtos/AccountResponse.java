package org.example.accountservice.dtos;

import lombok.*;
import org.example.accountservice.Models.UserResponseV2;
import org.example.accountservice.enums.State;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private Long idAccount ;
    private Long idUser ;
    private String registrationId ;
    private Double Balance;
    private String type ;
    private LocalDate createdAt;
    private UserResponseV2  user ;
    private State state ;



}
