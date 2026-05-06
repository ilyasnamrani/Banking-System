package org.example.accountservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.accountservice.Models.UserResponseV2;
import org.example.accountservice.enums.State;

import java.time.LocalDate;

@Entity @Getter
@Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount ;
    private Long idUser ;
    @Transient
    private UserResponseV2 user;
    private String registrationId ;
    private Double Balance;
    private String status ;
    private String type ;
    private State state ;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
